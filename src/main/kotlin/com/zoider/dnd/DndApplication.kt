package com.zoider.dnd

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

@SpringBootApplication
class DndApplication

fun main(args: Array<String>) {
    runApplication<DndApplication>(*args)
}
