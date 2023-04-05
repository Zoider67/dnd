package com.zoider.dnd.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DocumentReference

@Document
data class TgUser(
    @Id val id: String? = null,
    val name: String,
    val telegramId: String,
    @DocumentReference(lazy = true)
    val currentCharacter: Character? = null,
)