package com.zoider.dnd.bot.handlers.character

import com.zoider.dnd.bot.handlers.base.ICommandHandler
import com.zoider.dnd.services.CharacterServiceImpl
import com.zoider.dnd.utils.getSenderId
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class CharactersListHandler(
    private val characterServiceImpl: CharacterServiceImpl
) : ICommandHandler {
    override fun getFilter() = "/characters"

    override fun getDescription() = "Список персонажей"

    override fun execute(bot: AbsSender, update: Update) {
        val characters = characterServiceImpl.getCharacterList(update.getSenderId())
        val message = SendMessage.builder()
            .chatId(update.message.chatId)
            .text(characters?.map {
                "${it.name}, ${it.race}, ${it.charClass}"
            }?.joinToString(separator = "\n").toString())
            .build()
        bot.executeAsync(message)
    }
}