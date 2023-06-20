package com.zoider.dnd.bot.handlers.base

import com.zoider.dnd.dto.ConversationStateDto
import com.zoider.dnd.repositories.ConversationRepository
import org.telegram.telegrambots.meta.api.objects.Update

abstract class BaseConversationHandler(
    protected val conversationRepository: ConversationRepository
) : IConversationHandler {

    fun setConversationState(
        update: Update,
        state: IConversationHandler.State,
        attributes: Map<String, String>? = null
    ) {
        conversationRepository.saveConversationState(
            update.message.chatId.toString(),
            update.message.from.id.toString(),
            conversationStateDto = ConversationStateDto(
                conversationId = this.getConversationId(),
                state = state.toString(),
                convContextAttributes = attributes
            )
        )
    }

    fun clearConversationState(
        update: Update
    ) {
        conversationRepository.clearConversationState(
            chatId = update.message.chatId.toString(),
            tgUserId = update.message.from.id.toString()
        )
    }
}
