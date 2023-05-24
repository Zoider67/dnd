package com.zoider.dnd.repositories

import com.zoider.dnd.dto.ConversationStateDto
import com.zoider.dnd.utils.RedisClient
import org.springframework.stereotype.Repository

@Repository
class ConversationRepository(private val redisClient: RedisClient) {

    companion object {
        const val DEFAULT_TTL: Long = 86400 //1 day in seconds
        const val SPLITTER = ":"
    }

    fun saveConversationState(
        chatId: String,
        tgUserId: String,
        conversationStateDto: ConversationStateDto
    ) = redisClient.set(
        key = "${chatId}${SPLITTER}${tgUserId}",
        value = conversationStateDto.toString(),
        DEFAULT_TTL
    )

    fun getConversationState(
        chatId: String,
        telegramId: String
    ): ConversationStateDto? {
        val data = redisClient.get(key = "${chatId}${SPLITTER}${telegramId}")?.split(SPLITTER) ?: return null
        return ConversationStateDto(
            data[0],
            data[1]
        )
    }

    fun clearConversationState(
        chatId: String,
        tgUserId: String
    ) = redisClient.delete("${chatId}${SPLITTER}${tgUserId}")
}