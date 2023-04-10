package com.zoider.dnd.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DocumentReference

@Document
data class TgUser(
    @Id val id: String? = null,
    val telegramId: String,
    @DocumentReference(lazy = true /*, lookup = "{ 'telegramId' : ?#{#self._id} }" */)
    val currentCharacter: Character? = null,
)