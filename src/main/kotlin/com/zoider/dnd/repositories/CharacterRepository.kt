package com.zoider.dnd.repositories

import com.zoider.dnd.models.Character
import com.zoider.dnd.models.TgUser
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Repository

@Repository
class CharacterRepository(private val mongoTemplate: MongoTemplate) {

//    @Autowired TODO: check
//    private lateinit var mongoTemplate: MongoTemplate

    fun saveCharacter(character: Character): Character =
        mongoTemplate.save(character)

    fun getCharacterById(characterId: String): Character? =
        mongoTemplate.findById(characterId, Character::class.java)

    fun getCharactersByTelegramId(telegramId: String): List<Character>? {
        return mongoTemplate.find(Query.query(Criteria.where("tgUser.tgId").`is`(telegramId)), Character::class.java)
    }

    fun getCurrentCharacterOfUser(telegramId: String): Character? =
        mongoTemplate.findOne(Query.query(Criteria.where("telegramId").`is`(telegramId)), TgUser::class.java)?.currentCharacter

    fun setCurrentCharacterForUser(telegramId: String, character: Character) =
        mongoTemplate.updateFirst(
            Query.query(Criteria.where("telegramId").`is`(telegramId)),
            Update().set("currentCharacter", character),
            TgUser::class.java
        )

    fun deleteCharacterById(characterId: String) =
        mongoTemplate.findAndRemove(
            Query.query(Criteria.where("id").`is`(characterId)), Character::class.java
        )
}