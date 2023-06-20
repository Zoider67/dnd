package com.zoider.dnd.bot.handlers

import com.zoider.dnd.bot.handlers.base.ICommandHandler
import com.zoider.dnd.services.UserServiceImpl
import com.zoider.dnd.utils.getSenderId
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class StartCommandHandler(
    private val userServiceImpl: UserServiceImpl
) : ICommandHandler {
    override fun getFilter() = "/start"

    override fun getDescription() = "to_do"

    override fun execute(bot: AbsSender, update: Update) {
        val message = SendMessage.builder()
            .chatId(update.message.chatId)
            .text("onboarding_mgs_stub_to_do")
            .build()
        userServiceImpl.saveTelegramUser(update.getSenderId())
        bot.executeAsync(message)
    }
}