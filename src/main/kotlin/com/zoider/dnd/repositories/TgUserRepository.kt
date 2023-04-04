package com.zoider.dnd.repositories

import com.zoider.dnd.models.TgUser
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.findAndRemove
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class TgUserRepository constructor(
    private val mongoTemplate: MongoTemplate
) {

    fun saveTgUser(tgUser: TgUser): TgUser = mongoTemplate.save(tgUser)

    fun getTgUserById(tgUserId: String): TgUser? =
        mongoTemplate.findById(tgUserId, TgUser::class.java)

    fun deleteTgUserById(tgUserId: String) =
        mongoTemplate.findAndRemove(
            Query.query(Criteria.where("id").`is`(tgUserId)), TgUser::class.java
        )
}