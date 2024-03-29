package com.zoider.dnd.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Item(
    val name: String,
    val cost: Int? = null,
    val size: ItemSize,
    @Id var id: String? = null
)

enum class ItemSize {
    SMALL,
    MEDIUM,
    BIG
}