package com.zoider.dnd.bot.handlers.base

interface IConversationHandler: IHandler {

    fun getStates(): List<String>
}