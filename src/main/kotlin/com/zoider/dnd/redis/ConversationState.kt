package com.zoider.dnd.redis

import org.springframework.data.redis.core.RedisHash
import java.io.Serializable

@RedisHash("ConversationState")
data class ConversationState(
    val chatId: String,
    val tgUserId: String,
    val conversationType: String,
    val state: String
)