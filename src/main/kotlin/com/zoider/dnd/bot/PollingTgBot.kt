package com.zoider.dnd.bot

import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

@Component
class PollingTgBot(token: String) : TelegramLongPollingBot(token) {

    companion object {
        const val TG_TOKEN = "5560411660:AAFUApsI-0oBEnCzKOJzGU1YO4z6qkzDqiM"
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

            execute(message)
        }
    }
}