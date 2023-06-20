package com.zoider.dnd.repositories

import com.zoider.dnd.models.TgUser
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension

@DataMongoTest
@ExtendWith(SpringExtension::class)
class TgUserRepositoryTest @Autowired constructor(private val tgUserRepository: TgUserRepository) {
    @Test
    fun `create read update delete operations`() {
        val tgUser = TgUser(
            telegramId = "123456"
        )

        val userId = tgUserRepository.saveTgUser(tgUser).id.toString()
        val tgUserDb = tgUserRepository.getTgUserByTelegramId(tgUser.telegramId)

        assertNotNull(tgUserDb)

        assertEquals(tgUser.telegramId, tgUserDb?.telegramId)

        tgUserRepository.deleteTgUserById(userId)
        val tgUserDbNull = tgUserRepository.getTgUserByTelegramId(userId)

        assertNull(tgUserDbNull)
    }
}