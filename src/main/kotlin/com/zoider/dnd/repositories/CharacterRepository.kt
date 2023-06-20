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

    //region base CRUD operations
    fun saveCharacter(character: Character): Character =
        mongoTemplate.save(character)

    fun getCharacterById(characterId: String): Character? =
        mongoTemplate.findById(characterId, Character::class.java)

    fun getCharactersByUserTgId(telegramId: String): List<Character>? =
        mongoTemplate.find(Query.query(Criteria.where("tgUser.tgId").`is`(telegramId)), Character::class.java)

    fun getCurrentCharacterOfUser(telegramId: String): Character? =
        mongoTemplate.findOne(
            Query.query(Criteria.where("telegramId").`is`(telegramId)),
            TgUser::class.java
        )?.currentCharacter

    fun setCurrentCharacterForUser(telegramId: String, characterId: String): Boolean =
        mongoTemplate.updateFirst(
            Query.query(Criteria.where("telegramId").`is`(telegramId)),
            Update().set("currentCharacter", ObjectId(characterId)),
            TgUser::class.java
        ).wasAcknowledged()

    //TODO: write tests
    fun deleteCharacterById(characterId: String): Boolean =
        mongoTemplate.remove(
            Query.query(Criteria.where("id").`is`(characterId)), Character::class.java
        ).wasAcknowledged()
    //endregion

    fun updateCharacterName(characterId: String, name: String): Boolean =
        mongoTemplate.updateFirst(
            Query.query(Criteria.where("id").`is`(characterId)),
            Update().set("name", name),
            Character::class.java
        ).wasAcknowledged()

    fun updateCharacterClass(characterId: String, charClass: String): Boolean =
        mongoTemplate.updateFirst(
            Query.query(Criteria.where("id").`is`(characterId)),
            Update().set("charClass", charClass),
            Character::class.java
        ).wasAcknowledged()

    fun updateCharacterRace(characterId: String, race: String): Boolean =
        mongoTemplate.updateFirst(
            Query.query(Criteria.where("id").`is`(characterId)),
            Update().set("race", race),
            Character::class.java
        ).wasAcknowledged()

    fun setIsCharacterCreatedState(characterId: String, isCreated: Boolean): Boolean =
        mongoTemplate.updateFirst(
            Query.query(Criteria.where("id").`is`(characterId)),
            Update().set("isCreated", isCreated),
            Character::class.java
        ).wasAcknowledged()

    fun getNotCreatedCharacter(telegramId: String): Character? =
        mongoTemplate.findOne(
            Query.query(
                Criteria.where("tgUser.tgId").`is`(telegramId)
            ).addCriteria(
                Criteria.where("isCreated").`is`(false)
            ),
            Character::class.java
        )
}