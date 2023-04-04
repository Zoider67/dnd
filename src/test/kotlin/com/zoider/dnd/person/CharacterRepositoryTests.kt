package com.zoider.dnd.person

import com.zoider.dnd.models.Character
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.data.mongodb.core.MongoTemplate

@DataMongoTest
class CharacterRepositoryTests @Autowired constructor(
    val mongoTemplate: MongoTemplate
) {
    @Test
    fun `Create persons`() {
        val characterA = Character("nameA", 1)
        mongoTemplate.save(characterA)
        val characterB = Character("nameB", 2)
        mongoTemplate.save(characterB)

        val foundA = mongoTemplate.findById(characterA.id!!, Character::class.java)
        assertThat(foundA).isEqualTo(characterA)

        val foundB = mongoTemplate.findById(characterB.id!!, Character::class.java)
        assertThat(foundB).isEqualTo(characterB)
    }
}