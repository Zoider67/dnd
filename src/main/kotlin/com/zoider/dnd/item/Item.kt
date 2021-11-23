package com.zoider.dnd.item

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Item(
    var name: String,
    var cost: Integer,
    var weight: Double,
    @Id @GeneratedValue var id: Long? = null
)