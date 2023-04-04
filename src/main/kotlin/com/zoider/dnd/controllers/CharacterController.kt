//package com.zoider.dnd.controllers
//
//import com.zoider.dnd.models.Character
//import com.zoider.dnd.repositories.CharacterRepository
//import org.springframework.http.HttpStatus
//import org.springframework.web.bind.annotation.*
//import org.springframework.web.server.ResponseStatusException
//
//@RestController
//@RequestMapping("/api/person")
//class CharacterController(private val repository: CharacterRepository) {
//
//    @GetMapping("/{id}/")
//    fun getById(@PathVariable id: Long) =
//        repository.findById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "this person does not exist")
//
//    @GetMapping("/")
//    fun getAll() = repository.findAll()
//
//    @PostMapping("/")
//    fun create(@RequestBody character: Character): Character {
//        return repository.save(character)
//    }
//
//    @DeleteMapping("/{id}/")
//    fun delete(@PathVariable id: Long): Long {
//        repository.deleteById(id)
//        return id
//    }
//}