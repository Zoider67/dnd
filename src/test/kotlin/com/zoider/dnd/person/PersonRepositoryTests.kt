package com.zoider.dnd.person

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull

@DataJpaTest
class PersonRepositoryTests @Autowired constructor(
    val entityManager: TestEntityManager,
    val personRepository: PersonRepository
) {
    @Test
    fun `Create persons`() {
        val personA = Person("nameA", 1)
        entityManager.persist(personA)

        val personB = Person("nameB", 2)
        entityManager.persist(personB)

        entityManager.flush()

        val foundA = personRepository.findByIdOrNull(personA.id!!)
        assertThat(foundA).isEqualTo(personA)

        val foundB = personRepository.findByIdOrNull(personB.id!!)
        assertThat(foundB).isEqualTo(personB)
    }
}