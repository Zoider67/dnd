package com.zoider.dnd.bot.handlers

import com.zoider.dnd.bot.handlers.base.BaseConversationHandler
import com.zoider.dnd.bot.handlers.base.ICommandHandler
import com.zoider.dnd.bot.handlers.base.IConversationHandler
import com.zoider.dnd.repositories.ConversationRepository
import com.zoider.dnd.services.EditCharacterServiceImpl
import com.zoider.dnd.utils.SendMessageFactory
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class CreateCharacterCommandHandler(
    conversationRepository: ConversationRepository,
    private val editCharacterServiceImpl: EditCharacterServiceImpl
) : BaseConversationHandler(conversationRepository), ICommandHandler {
    override fun getFilter() = "/new_character"

    override fun getDescription() = "Создать персонажа"
    override fun getConversationId(): String = "new_character"

    private enum class CreateCharacterState : IConversationHandler.State {
        NAME_STEP,
        CLASS_STEP,
        RACE_STEP,
        COMPLETED
    }

    override fun getStates(): List<IConversationHandler.State> = CreateCharacterState.values().toList()

    override fun execute(bot: AbsSender, update: Update, state: String) {
        when (state) {
            CreateCharacterState.NAME_STEP.toString() -> {
                setConversationState(update, CreateCharacterState.CLASS_STEP)
                val message = SendMessageFactory.textToChat(update.message.chatId, "Класс персонажа:")
                bot.executeAsync(message)
            }

            CreateCharacterState.CLASS_STEP.toString() -> {
                setConversationState(update, CreateCharacterState.RACE_STEP)
                val message = SendMessageFactory.textToChat(update.message.chatId, "Раса персонажа:")
                bot.executeAsync(message)
            }

            CreateCharacterState.RACE_STEP.toString() -> {
                setConversationState(update, CreateCharacterState.COMPLETED)
                val message = SendMessageFactory.textToChat(update.message.chatId, "Персонаж создан")
                bot.executeAsync(message)
            }

            CreateCharacterState.COMPLETED.toString() -> {
                conversationRepository.clearConversationState(
                    chatId = update.message.chatId.toString(),
                    tgUserId = update.message.from.id.toString()
                )
                val message = SendMessageFactory.textToChat(update.message.chatId, "Ок")
                bot.executeAsync(message)
            }
        }
    }

    override fun execute(bot: AbsSender, update: Update) {

        setConversationState(update, CreateCharacterState.NAME_STEP)
        val message = SendMessageFactory.textToChat(update.message.chatId, "Имя персонажа:")
        bot.executeAsync(message)
    }
}