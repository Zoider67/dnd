package com.zoider.dnd.services

import com.zoider.dnd.dto.CharacterDto
import com.zoider.dnd.models.Character
import com.zoider.dnd.repositories.CharacterRepository
import com.zoider.dnd.repositories.TgUserRepository
import com.zoider.dnd.utils.exceptions.CommonDndBotException
import org.springframework.stereotype.Service

@Service
class CharacterServiceImpl(
    private val tgUserRepository: TgUserRepository,
    private val characterRepository: CharacterRepository
) : CharacterService {

    override fun createCharacter(userTgId: String, characterDto: CharacterDto): Character {
        val user = tgUserRepository.getTgUserByTelegramId(userTgId)
            ?: throw CommonDndBotException("Ошибка создания персонажа: пользователь не зарегистрирован")
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
        return characterRepository.getCharactersByUserTgId(userTgId)
    }

    override fun setCurrentCharacterByName(userTgId: String, characterName: String): Character? {
        val character = characterRepository.getCharacterByName(characterName) ?: return null
        val acknowledged = characterRepository.setCurrentCharacterForUser(
            telegramId = userTgId,
            characterId = character.id.toString()
        )
        if(!acknowledged) {
            throw CommonDndBotException("Ошибка выбора персонажа")
        }
        return character
    }

    override fun getCurrentCharacter(userTgId: String): Character? {
        return characterRepository.getCurrentCharacterOfUser(telegramId = userTgId)
    }
}