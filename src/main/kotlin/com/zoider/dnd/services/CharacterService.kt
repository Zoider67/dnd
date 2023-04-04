package com.zoider.dnd.services

import com.zoider.dnd.models.Character
import com.zoider.dnd.models.TgUser

interface CharacterService {

    fun createCharacter(character: Character): Character

    fun getCharacterList(userId: String): List<Character>

    fun setCurrentCharacter(characterId: String): Character
}