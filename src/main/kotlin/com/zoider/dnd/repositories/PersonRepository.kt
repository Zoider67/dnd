package com.zoider.dnd.repositories

import com.zoider.dnd.models.Person
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.repository.CrudRepository

interface PersonRepository: MongoRepository<Person, Long> {
}