package com.zoider.dnd.models

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Character(
    val name: String,
    val level: Int,
    @Id val id: String? = null
)