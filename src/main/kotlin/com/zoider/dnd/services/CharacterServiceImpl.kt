package com.zoider.dnd.services

import com.zoider.dnd.dto.CharacterDto
import com.zoider.dnd.models.Character
import com.zoider.dnd.models.TgUser
import com.zoider.dnd.repositories.CharacterRepository
import com.zoider.dnd.repositories.TgUserRepository
import com.zoider.dnd.utils.exceptions.BaseDndBotException
import org.springframework.stereotype.Service
import java.lang.Exception

@Service
class CharacterServiceImpl(
    val tgUserRepository: TgUserRepository,
    val characterRepository: CharacterRepository
) : CharacterService {

    override fun createCharacter(userTgId: String, characterDto: CharacterDto): Character {
        val user = tgUserRepository.getTgUserById(userTgId)
            ?: throw BaseDndBotException("Ошибка создания персонажа: пользователь не зарегистрирован")
        return characterRepository.saveCharacter(
            Character(
                tgUser = user,
                name = characterDto.name,
                charClass = characterDto.charClass,
                race = characterDto.race
            )
        )
    }

    override fun getCharacterList(userTgId: String): List<Character>? {
        return characterRepository.getCharactersByTelegramId(userTgId)
    }

    override fun setCurrentCharacter(userTgId: String, characterId: String): Boolean {
        return characterRepository.setCurrentCharacterForUser(
            telegramId = userTgId,
            characterId = characterId
        ).wasAcknowledged()
    }
}