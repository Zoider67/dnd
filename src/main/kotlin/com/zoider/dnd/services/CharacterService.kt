package com.zoider.dnd.services

import com.zoider.dnd.dto.CharacterDto
import com.zoider.dnd.models.Character
import com.zoider.dnd.models.TgUser

interface CharacterService {

    fun createCharacter(userTgId: String, characterDto: CharacterDto): Character

    fun getCharacterList(userTgId: String): List<Character>?

    fun setCurrentCharacter(userTgId: String ,characterId: String): Boolean
}