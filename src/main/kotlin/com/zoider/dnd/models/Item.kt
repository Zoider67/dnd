package com.zoider.dnd.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Item(
    var name: String,
    var cost: Integer,
    var weight: Double,
    @Id var id: String? = null
)