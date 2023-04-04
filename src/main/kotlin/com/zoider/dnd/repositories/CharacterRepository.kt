package com.zoider.dnd.repositories

import com.zoider.dnd.models.Character
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
class CharacterRepository constructor(
    private val mongoTemplate: MongoTemplate
) {

//    @Autowired TODO: check
//    private lateinit var mongoTemplate: MongoTemplate

    fun saveCharacter(character: Character): Character =
        mongoTemplate.save(character)

    fun getCharacterById(characterId: String): Character? =
        mongoTemplate.findById(characterId, Character::class.java)

    fun deleteCharacterById(characterId: String) =
        mongoTemplate.findAndRemove(
            Query.query(Criteria.where("id").`is`(characterId)), Character::class.java
        )
}