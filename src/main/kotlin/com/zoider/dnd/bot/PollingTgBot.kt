package com.zoider.dnd.bot

import com.zoider.dnd.bot.handlers.base.ICommandHandler
import com.zoider.dnd.bot.handlers.base.IHandler
import com.zoider.dnd.bot.handlers.base.ITextHandler
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands.SetMyCommandsBuilder
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand

@Component
class PollingTgBot(
    @Value("\${telegram.token}")
    token: String,
    private val handlers: List<IHandler>
) : TelegramLongPollingBot(token) {

    companion object {
        const val TG_BOT_NAME = "rpg_bot_rc"
    }

    override fun getBotUsername(): String {
        return TG_BOT_NAME
    }

    override fun onRegister() {
        super.onRegister()
        println("Register commands: \n${handlers.joinToString { it.getFilter().plus("\n") }}")
        val commands = SetMyCommands
            .builder()
            .commands(handlers
                .filterIsInstance<ICommandHandler>()
                .map { BotCommand(it.getFilter(), it.getDescription()) }
            ).build()
        execute(commands)
    }

    override fun onUpdateReceived(update: Update?) {
        if (update?.hasMessage() == true) {
            var handler: IHandler? = null
            if (update.message.isCommand) {
                handler = handlers.filterIsInstance<ICommandHandler>().find { it.getFilter() == update.message.text }
            } else if (update.message.hasText()) {
                handler = handlers.filterIsInstance<ITextHandler>().find { it.getFilter() == update.message.text }
            } else {
                //TODO
                println("no handlers for that kind of messages")
            }
            handler?.execute(this, update)
        }
    }
}