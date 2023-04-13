package com.zoider.dnd.dto

import com.zoider.dnd.bot.handlers.base.IConversationHandler

data class ConversationStateDto(
    val conversationId: String,
    val state: String
) {
    companion object {
        fun ofHandler(conversationHandler: IConversationHandler, state: String) =
            ConversationStateDto(conversationHandler.getConversationId(), state)
    }

    override fun toString(): String {
        return "${conversationId}_${state}"
    }
}