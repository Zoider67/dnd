package com.zoider.dnd

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

@SpringBootApplication
@ComponentScan("com.zoider")
class DndApplication

fun main(args: Array<String>) {
    runApplication<DndApplication>(*args)
}
