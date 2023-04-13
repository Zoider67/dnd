package com.zoider.dnd.repositories

import com.zoider.dnd.models.Character
import com.zoider.dnd.models.TgUser
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit.jupiter.SpringExtension

@DataMongoTest
@DirtiesContext
@ExtendWith(SpringExtension::class)
class CharacterRepositoryTest @Autowired constructor(
    private val characterRepository: CharacterRepository,
    private val tgUserRepository: TgUserRepository
) {

    @Test
    fun `create read update delete operations`() {
        val user = tgUserRepository.saveTgUser(
            TgUser(
                telegramId = "123456"
            )
        )

        assertNotNull(user)

        val character1 = Character(
            tgUser = user,
            name = "vasya's first character",
            charClass = "rogue",
            race = "human"
        )

        val character2 = Character(
            tgUser = user,
            name = "vasya's second character",
            charClass = "wizard",
            race = "elf"
        )

        val charDb1 = characterRepository.saveCharacter(character1)
        val charDb2 = characterRepository.saveCharacter(character2)

        val characters = characterRepository.getCharactersByTelegramId(user.telegramId)
        assertTrue(characters?.size == 2)

        characterRepository.setCurrentCharacterForUser(telegramId = user.telegramId, charDb1.id!!)

        assertEquals(character1.name, characterRepository.getCurrentCharacterOfUser(user.telegramId)?.name)

        characterRepository.setCurrentCharacterForUser(telegramId = user.telegramId, charDb2.id!!)

        assertEquals(character2.name, characterRepository.getCurrentCharacterOfUser(user.telegramId)?.name)
    }
}