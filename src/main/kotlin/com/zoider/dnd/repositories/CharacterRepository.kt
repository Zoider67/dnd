package com.zoider.dnd.repositories

import com.zoider.dnd.models.Character
import com.zoider.dnd.models.TgUser
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.findById
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.UntypedExampleMatcher.matching
import org.springframework.data.mongodb.core.query.Update
import org.springframework.data.mongodb.core.query.where
import org.springframework.stereotype.Repository

@Repository
class CharacterRepository(private val mongoTemplate: MongoTemplate) {

//    @Autowired TODO: check
//    private lateinit var mongoTemplate: MongoTemplate

    fun saveCharacter(character: Character): Character =
        mongoTemplate.save(character)

    fun getCharacterById(characterId: String): Character? =
        mongoTemplate.findById(characterId, Character::class.java)

    fun getCharactersByUserId(tgUserId: String): List<Character>? =
        mongoTemplate.find(Query.query(Criteria.where("tgUser").`is`(tgUserId)), Character::class.java)

    fun getCurrentCharacterOfUser(tgUserId: String): Character? =
        mongoTemplate.findById(tgUserId, TgUser::class.java)?.currentCharacter

    fun setCurrentCharacterForUser(tgUserId: String, character: Character) =
        mongoTemplate.updateFirst(
            Query.query(Criteria.where("id").`is`(tgUserId)),
            Update().set("currentCharacter", character),
            TgUser::class.java
        )

    fun deleteCharacterById(characterId: String) =
        mongoTemplate.findAndRemove(
            Query.query(Criteria.where("id").`is`(characterId)), Character::class.java
        )
}