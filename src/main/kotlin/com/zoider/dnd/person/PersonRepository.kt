package com.zoider.dnd.person

import org.springframework.data.repository.CrudRepository

interface PersonRepository: CrudRepository<Person, Long> {
}