package com.zoider.dnd.bot.handlers.base

import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

interface IHandler {

    fun getFilter(): String

    fun execute(bot: AbsSender, update: Update)
}