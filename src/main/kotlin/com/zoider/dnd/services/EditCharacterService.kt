package com.zoider.dnd.services

import com.zoider.dnd.models.Character

interface EditCharacterService {

    fun startCreating(userTgId: String): String?

    fun getNotCreatedCharacter(userTgId: String): Character?

    fun stopCreating(charId: String): Character?

    fun setName(charId: String, name: String): Boolean

    fun setCharClass(charId: String, charClass: String): Boolean

    fun setRace(charId: String, race: String): Boolean
}