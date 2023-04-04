package com.zoider.dnd.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class TgUser (
    val name: String,
    val telegramId: String,
    @Id val id: String? = null
)