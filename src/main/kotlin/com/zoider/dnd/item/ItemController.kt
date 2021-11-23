package com.zoider.dnd.item

import com.zoider.dnd.person.Person
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/item")
class ItemController(private val repository: ItemRepository) {

    @GetMapping("/{id}/")
    fun getById(@PathVariable id: Long) =
        repository.findById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "this item does not exist")

    @GetMapping("/")
    fun getAll() = repository.findAll()

    @PostMapping("/")
    fun create(@RequestBody item: Item): Item {
        return repository.save(item)
    }

    @DeleteMapping("/{id}/")
    fun delete(@PathVariable id: Long): Long {
        repository.deleteById(id)
        return id
    }
}