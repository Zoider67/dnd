package com.zoider.dnd.bot

import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

@Component
class PollingTgBot(@Value("\${telegram.token}") token: String) : TelegramLongPollingBot(token) {

    companion object {
        const val TG_BOT_NAME = "rpg_bot_rc"
    }
    override fun getBotUsername(): String {
        return TG_BOT_NAME
    }

    override fun onUpdateReceived(update: Update?) {
        if(update?.hasMessage() == true && update.message?.hasText() == true) {
            val message = SendMessage()
            message.setChatId(update.message.chatId)
            message.text = update.message.text
            val user: User
            execute(message)
        }
    }
}