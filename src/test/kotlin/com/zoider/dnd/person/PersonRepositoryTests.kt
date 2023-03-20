package com.zoider.dnd.person

import com.zoider.dnd.models.Person
import com.zoider.dnd.repositories.PersonRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.findOne
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.repository.findByIdOrNull

@DataMongoTest
class PersonRepositoryTests @Autowired constructor(
    val mongoTemplate: MongoTemplate
) {
    @Test
    fun `Create persons`() {
        val personA = Person("nameA", 1)
        mongoTemplate.save(personA)
        val personB = Person("nameB", 2)
        mongoTemplate.save(personB)

        val foundA = mongoTemplate.findById(personA.id!!, Person::class.java)
        assertThat(foundA).isEqualTo(personA)

        val foundB = mongoTemplate.findById(personB.id!!, Person::class.java)
        assertThat(foundB).isEqualTo(personB)
    }
}