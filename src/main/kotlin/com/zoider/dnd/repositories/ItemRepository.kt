package com.zoider.dnd.repositories

import com.zoider.dnd.models.Item
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.repository.CrudRepository

interface ItemRepository: MongoRepository<Item, Long> {
}