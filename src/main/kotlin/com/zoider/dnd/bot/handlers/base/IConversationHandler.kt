package com.zoider.dnd.bot.handlers.base

import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender

interface IConversationHandler: IHandler {

    fun getConversationId(): String

    fun getStates(): List<String> //TODO: write an interface IState with getValue(): String (or Int???), get from this function as IState

    fun execute(bot: AbsSender, update: Update, state: String)
}