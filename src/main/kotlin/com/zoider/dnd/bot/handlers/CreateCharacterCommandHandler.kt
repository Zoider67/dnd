package com.zoider.dnd.bot.handlers

import com.zoider.dnd.bot.handlers.base.ICommandHandler
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class CreateCharacterCommandHandler : ICommandHandler {
    override fun getFilter() = "/new_character"

    override fun getDescription() = "Создать персонажа"

    override fun execute(bot: AbsSender, update: Update) {
        val message = SendMessage.builder()
            .chatId(update.message.chatId)
            .text("create character reply")
            .build()
        bot.executeAsync(message)
    }
}