package com.zoider.dnd.bot.handlers

import com.zoider.dnd.bot.PollingTgBot
import com.zoider.dnd.bot.handlers.base.ICommandHandler
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class CharactersListCommandHandler: ICommandHandler {
    override fun getFilter() = "/characters"

    override fun getDescription() = "Список персонажей"

    override fun execute(bot: AbsSender, update: Update) {
        val message = SendMessage.builder()
            .chatId(update.message.chatId)
            .text("characters list reply")
            .build()
        bot.executeAsync(message)
    }
}