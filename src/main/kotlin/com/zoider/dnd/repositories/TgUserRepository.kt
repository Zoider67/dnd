package com.zoider.dnd.repositories

import com.zoider.dnd.models.TgUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.findAndRemove
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class TgUserRepository(private val mongoTemplate: MongoTemplate) {

    fun saveTgUser(tgUser: TgUser): TgUser = mongoTemplate.save(tgUser)

    fun getTgUserById(tgUserId: String): TgUser? =
        mongoTemplate.findById(tgUserId, TgUser::class.java)

    fun deleteTgUserById(tgUserId: String) =
        mongoTemplate.findAndRemove(
            Query.query(Criteria.where("id").`is`(tgUserId)), TgUser::class.java
        )

    fun getTgUserByTelegramId(telegramId: String): TgUser? =
        mongoTemplate.findOne(Query.query(Criteria.where("telegramId").`is`(telegramId)), TgUser::class.java)
}