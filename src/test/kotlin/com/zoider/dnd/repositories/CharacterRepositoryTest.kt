package com.zoider.dnd.repositories

import com.zoider.dnd.models.Character
import com.zoider.dnd.models.TgUser
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@DataMongoTest
@ExtendWith(SpringExtension::class)
class CharacterRepositoryTest @Autowired constructor(
    private val characterRepository: CharacterRepository,
    private val tgUserRepository: TgUserRepository
) {

    @Test
    fun `create read update delete operations`() {
        val user = tgUserRepository.saveTgUser(
            TgUser(
                name = "vasya",
                telegramId = "123456"
            )
        )

        val character = Character(
            tgUser = user,
            name = "vasya character",
            level = 1
        )


    }
}