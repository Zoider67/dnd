package com.zoider.dnd.bot.handlers.character

import com.zoider.dnd.bot.handlers.base.ICommandHandler
import com.zoider.dnd.bot.handlers.utils.toReadableString
import com.zoider.dnd.services.CharacterServiceImpl
import com.zoider.dnd.utils.SendMessageFactory
import com.zoider.dnd.utils.getSenderId
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class CurrentCharacterHandler(
    private val characterServiceImpl: CharacterServiceImpl
) : ICommandHandler {
    override fun getDescription(): String = "Информация о текущем персонаже"

    override fun getFilter(): String = "/current_character"


    override fun execute(bot: AbsSender, update: Update) {
        val character = characterServiceImpl.getCurrentCharacter(update.getSenderId())
        if (character == null) {
            val message = SendMessageFactory.textToChat(update.message.chatId, "Персонаж с таким именем не найден")
            bot.executeAsync(message)
        } else {
            val message = SendMessageFactory.textToChat(update.message.chatId, character.toReadableString())

            val inlineKeyboardMarkup = InlineKeyboardMarkup()

            val testBtn = InlineKeyboardButton("test")
            testBtn.callbackData = "test"

            inlineKeyboardMarkup.keyboard = mutableListOf(mutableListOf(testBtn))

            message.replyMarkup = inlineKeyboardMarkup

            bot.executeAsync(message)
        }
    }
}