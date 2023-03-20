package com.zoider.dnd.models

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Person(
    var name: String,
    var level: Int,
    @Id var id: String? = null
)