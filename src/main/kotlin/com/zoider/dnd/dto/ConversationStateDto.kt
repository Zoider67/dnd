package com.zoider.dnd.dto

import com.zoider.dnd.bot.handlers.base.IConversationHandler

data class ConversationStateDto(
    val conversationId: String,
    val state: String,
    val convContextAttributes: Map<String, String>? = null
) {
    companion object {

        const val CONTEXT_ATTRS_SEPARATOR = "|"
        const val KEY_VALUE_SEPARATOR = ","
        fun ofHandler(
            conversationHandler: IConversationHandler,
            state: String,
            attributes: Map<String, String>
        ) = ConversationStateDto(
            conversationId = conversationHandler.getConversationId(),
            state = state,
            convContextAttributes = attributes
        )

        fun fromRedisString(value: String): ConversationStateDto? {
            val splitValue = value.split(":")
            if (splitValue.size < 2) {
                return null
            }
            val conversationId = splitValue[0]
            val state = splitValue[1]
            val attributes = if (splitValue.size >= 3)
                mapStringToAttributes(splitValue[2]) else null
            return ConversationStateDto(
                conversationId = conversationId,
                state = state,
                convContextAttributes = attributes
            )
        }

        private fun mapStringToAttributes(values: String): Map<String, String> {
            val attributes: MutableMap<String, String> = mutableMapOf()
            values.split(CONTEXT_ATTRS_SEPARATOR).forEach {
                val pair = it.split(KEY_VALUE_SEPARATOR)
                if (pair.size != 2) {
                    return attributes
                }
                attributes.put(key = pair[0], value = pair[1])
            }
            return attributes
        }
    }

    override fun toString(): String {
        return "${conversationId}:${state}:${mapAttributesToStr().orEmpty()}"
    }

    private fun mapAttributesToStr(): String? {
        return convContextAttributes?.map {
            "${it.key}${KEY_VALUE_SEPARATOR}${it.value}"
        }?.joinToString(separator = CONTEXT_ATTRS_SEPARATOR)
    }
}


// chat_id, user_id <=> conversation_id, conversation_state, attributes
//conversation_id:conversation_state:|key1,value1|key2,value2|