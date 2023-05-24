package com.zoider.dnd.services

import com.zoider.dnd.models.Character
import com.zoider.dnd.repositories.CharacterRepository
import com.zoider.dnd.repositories.TgUserRepository
import com.zoider.dnd.utils.exceptions.CommonDndBotException
import org.springframework.stereotype.Service

@Service
class EditCharacterServiceImpl(
    private val tgUserRepository: TgUserRepository,
    private val characterRepository: CharacterRepository
) : EditCharacterService {
    override fun startCreating(userTgId: String) {
        val user = tgUserRepository.getTgUserById(userTgId)
            ?: throw CommonDndBotException("Ошибка создания персонажа: пользователь не зарегистрирован")
        characterRepository.saveCharacter(
            Character(
                tgUser = user,
                isCreated = false,
                name = "",
                charClass = "",
                race = ""
            )
        )
    }

    override fun getNotCreatedCharacter(userTgId: String): Character? {
        return characterRepository.getNotCreatedCharacter(userTgId)
    }

    override fun stopCreating(charId: String) =
        characterRepository.setIsCharacterCreatedState(
            characterId = charId,
            isCreated = false
        )

    override fun setName(charId: String, name: String) =
        characterRepository.updateCharacterName(
            characterId = charId,
            name = name
        )

    override fun setCharClass(charId: String, charClass: String) =
        characterRepository.updateCharacterClass(
            characterId = charId,
            charClass = charClass
        )

    override fun setRace(charId: String, race: String) =
        characterRepository.updateCharacterRace(
            characterId = charId,
            race = race
        )
}