package com.zoider.dnd.models

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DocumentReference
import org.springframework.data.mongodb.core.mapping.Field

@Document
data class Character(
    @Id val id: String? = null,
    @DocumentReference(lazy = true, lookup = "{ 'telegramId' : ?#{#self.tgId} }")
    val tgUser: TgUser,
    val name: String,
    val level: Int
)