package com.zoider.dnd.utils

import org.telegram.telegrambots.meta.api.methods.send.SendMessage

object SendMessageFactory {
    fun textToChat(chatId: Long, text: String) =
        SendMessage.builder()
            .chatId(chatId)
            .text(text)
            .build()

}