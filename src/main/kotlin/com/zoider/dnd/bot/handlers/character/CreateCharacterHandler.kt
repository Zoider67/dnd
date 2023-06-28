package com.zoider.dnd.bot.handlers.character

import com.zoider.dnd.bot.handlers.base.BaseConversationHandler
import com.zoider.dnd.bot.handlers.base.ICommandHandler
import com.zoider.dnd.bot.handlers.base.IConversationHandler
import com.zoider.dnd.dto.ConversationStateDto
import com.zoider.dnd.repositories.ConversationRepository
import com.zoider.dnd.services.EditCharacterServiceImpl
import com.zoider.dnd.utils.SendMessageFactory
import com.zoider.dnd.utils.exceptions.CommonDndBotException
import com.zoider.dnd.utils.getSenderId
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class CreateCharacterHandler(
    conversationRepository: ConversationRepository,
    private val editCharacterServiceImpl: EditCharacterServiceImpl
) : BaseConversationHandler(conversationRepository), ICommandHandler {

    companion object {
        const val CHAR_ID_ATTR = "CHAR_ID"
    }

    override fun getFilter() = "/new_character"

    override fun getDescription() = "Создать персонажа"
    override fun getConversationId(): String = "NEW_CHARACTER"

    private enum class CreateCharacterState : IConversationHandler.State {
        NAME_STEP,
        CLASS_STEP,
        RACE_STEP,
        COMPLETED
    }

    override fun getStates(): List<IConversationHandler.State> = CreateCharacterState.values().toList()

    override fun execute(bot: AbsSender, update: Update, stateDto: ConversationStateDto) {

        val charId = stateDto.convContextAttributes?.get(CHAR_ID_ATTR)
            ?: throw CommonDndBotException("Missing character id in conversation context")

        when (stateDto.state) {
            CreateCharacterState.NAME_STEP.toString() -> {
                editCharacterServiceImpl.setName(charId = charId, name = update.message.text)
                setConversationState(
                    update = update,
                    state = CreateCharacterState.CLASS_STEP,
                    attributes = mapOf(Pair(CHAR_ID_ATTR, charId))
                )
                val message = SendMessageFactory.textToChat(update.message.chatId, "Класс персонажа:")
                bot.executeAsync(message)
            }

            CreateCharacterState.CLASS_STEP.toString() -> {
                editCharacterServiceImpl.setCharClass(charId = charId, charClass = update.message.text)
                setConversationState(
                    update = update,
                    state = CreateCharacterState.RACE_STEP,
                    attributes = mapOf(Pair(CHAR_ID_ATTR, charId))
                )
                val message = SendMessageFactory.textToChat(update.message.chatId, "Раса персонажа:")
                bot.executeAsync(message)
            }

            CreateCharacterState.RACE_STEP.toString() -> {
                editCharacterServiceImpl.setRace(charId = charId, race = update.message.text)
                setConversationState(
                    update = update,
                    state = CreateCharacterState.COMPLETED,
                    attributes = mapOf(Pair(CHAR_ID_ATTR, charId))
                )
                val message = SendMessageFactory.textToChat(update.message.chatId, "Персонаж создан")
                bot.executeAsync(message)
            }

            CreateCharacterState.COMPLETED.toString() -> {
                val character = editCharacterServiceImpl.stopCreating(charId)
                    ?: throw CommonDndBotException()
                clearConversationState(update)
                val characterDescription = "" +
                        "Имя: ${character.name}\n" +
                        "Класс: ${character.charClass}\n" +
                        "Раса: ${character.race}"
                val message = SendMessageFactory.textToChat(update.message.chatId, characterDescription)
                bot.executeAsync(message)
            }
        }
    }

    override fun execute(bot: AbsSender, update: Update) {
        val charId = editCharacterServiceImpl.startCreating(userTgId = update.getSenderId())
            ?: throw CommonDndBotException()
        setConversationState(
            update = update,
            state = CreateCharacterState.NAME_STEP,
            attributes = mapOf(Pair(CHAR_ID_ATTR, charId))
        )
        val message = SendMessageFactory.textToChat(update.message.chatId, "Имя персонажа:")
        bot.executeAsync(message)
    }
}