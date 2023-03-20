package com.zoider.dnd.controllers

import com.zoider.dnd.models.Person
import com.zoider.dnd.repositories.PersonRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/person")
class PersonController(private val repository: PersonRepository) {

    @GetMapping("/{id}/")
    fun getById(@PathVariable id: Long) =
        repository.findById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "this person does not exist")

    @GetMapping("/")
    fun getAll() = repository.findAll()

    @PostMapping("/")
    fun create(@RequestBody person: Person): Person {
        return repository.save(person)
    }

    @DeleteMapping("/{id}/")
    fun delete(@PathVariable id: Long): Long {
        repository.deleteById(id)
        return id
    }
}   