package com.zoider.dnd.bot.handlers.utils

import com.zoider.dnd.models.Character

fun Character.toReadableString(): String {
    return "${this.name}, ${this.race}, ${this.charClass}"
}