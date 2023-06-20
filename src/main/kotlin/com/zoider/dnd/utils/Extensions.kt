package com.zoider.dnd.utils

import org.telegram.telegrambots.meta.api.objects.Update

fun Update.getSenderId(): String = this.message.from.id.toString()

fun Update.getChatId(): String = this.message.chatId.toString()