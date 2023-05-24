package com.zoider.dnd.bot.handlers.base

import com.zoider.dnd.dto.ConversationStateDto
import com.zoider.dnd.repositories.ConversationRepository
import org.telegram.telegrambots.meta.api.objects.Update

abstract class BaseConversationHandler(
    protected val conversationRepository: ConversationRepository
) : IConversationHandler {
    fun getConversationRepository(): ConversationRepository = conversationRepository

    fun setConversationState(update: Update, state: IConversationHandler.State) {
        this.getConversationRepository().saveConversationState(
            update.message.chatId.toString(),
            update.message.from.id.toString(),
            conversationStateDto = ConversationStateDto(
                conversationId = this.getConversationId(),
                state = state.toString()
            )
        )
    }
}
