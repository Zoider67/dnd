package com.zoider.dnd.bot.handlers.character

import com.zoider.dnd.bot.handlers.base.ITextHandler
import com.zoider.dnd.services.CharacterServiceImpl
import com.zoider.dnd.utils.SendMessageFactory
import com.zoider.dnd.utils.getSenderId
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class SetCurrentCharacterHandler(
    private val characterServiceImpl: CharacterServiceImpl
) : ITextHandler {
    override fun getFilter(): String = "Выбрать персонажа .+"

    override fun execute(bot: AbsSender, update: Update) {
        val characterName = update.message.text.removePrefix("Выбрать персонажа ")
        val character = characterServiceImpl.setCurrentCharacterByName(
            userTgId = update.getSenderId(),
            characterName = characterName
        )
        if (character == null) {
            val message = SendMessageFactory.textToChat(update.message.chatId, "Персонаж с таким именем не найден")
            bot.executeAsync(message)
        } else {
            val message = SendMessageFactory.textToChat(update.message.chatId, "Выбран персонаж ${character.name}")
            bot.executeAsync(message)
        }
    }
}