package com.zoider.dnd.person

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
class Person(
    var name: String,
    var level: Int,
    @Id @GeneratedValue var id: Long? = null
)