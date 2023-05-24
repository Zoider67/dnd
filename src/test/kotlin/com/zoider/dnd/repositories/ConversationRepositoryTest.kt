package com.zoider.dnd.repositories

import com.zoider.dnd.dto.ConversationStateDto
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

//TODO: automatically run redis container for unit test

@SpringBootTest
class ConversationRepositoryTest @Autowired constructor(
    private val conversationRepository: ConversationRepository
) {

    @Test
    fun `conversation states`() {

        val testConvId1 = "testconvid1"
        val testState1 = "teststate1"

        val testConvId2 = "testconvid2"
        val testState2 = "teststate2"

        val testChatId = "testchatid"
        val testUserId = "testuserid"

        val conversationStateDto1 = ConversationStateDto(testConvId1, testState1)

        conversationRepository.saveConversationState(
            chatId = testChatId,
            tgUserId = testUserId,
            conversationStateDto = conversationStateDto1
        )

        val savedConversationStateDto1 = conversationRepository.getConversationState(
            chatId = testChatId,
            telegramId = testUserId
        )

        assertEquals(
            conversationStateDto1.toString(),
            savedConversationStateDto1.toString()
        )

        val conversationStateDto2 = ConversationStateDto(testConvId2, testState2)

        conversationRepository.saveConversationState(
            chatId = testChatId,
            tgUserId = testUserId,
            conversationStateDto = conversationStateDto2
        )

        val savedConversationStateDto2 = conversationRepository.getConversationState(
            chatId = testChatId,
            telegramId = testUserId
        )

        assertEquals(
            conversationStateDto2.toString(),
            savedConversationStateDto2.toString()
        )

        assertNull(
            conversationRepository.getConversationState(
                chatId = testChatId,
                telegramId = "anotheruser"
            )
        )

        conversationRepository.clearConversationState(
            chatId = testChatId,
            tgUserId = testUserId
        )

        assertNull(
            conversationRepository.getConversationState(
                chatId = testChatId,
                telegramId = testUserId
            )
        )
    }
}