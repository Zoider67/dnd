package com.zoider.dnd.item

import org.springframework.data.repository.CrudRepository

interface ItemRepository: CrudRepository<Item, Long> {
}