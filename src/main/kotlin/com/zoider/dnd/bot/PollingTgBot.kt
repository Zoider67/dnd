package com.zoider.dnd.bot

import com.zoider.dnd.bot.handlers.base.ICommandHandler
import com.zoider.dnd.bot.handlers.base.IConversationHandler
import com.zoider.dnd.bot.handlers.base.IHandler
import com.zoider.dnd.bot.handlers.base.ITextHandler
import com.zoider.dnd.repositories.ConversationRepository
import com.zoider.dnd.utils.SendMessageFactory
import com.zoider.dnd.utils.exceptions.MissingHandlerException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand

@Component
class PollingTgBot(
    @Value("\${telegram.token}")
    token: String,
    private val handlers: List<IHandler>,
    private val conversationRepository: ConversationRepository
) : TelegramLongPollingBot(token) {

    companion object {
        const val TG_BOT_NAME = "rpg_bot_rc"
    }

    override fun getBotUsername(): String {
        return TG_BOT_NAME
    }

    override fun onRegister() {
        super.onRegister()
        println("Register commands: \n${handlers.joinToString { it.getFilter() + "\n" }}")
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
            try {
                processHandlers(update)
            } catch (e: MissingHandlerException) {
                val msg = SendMessageFactory.textToChat(update.message.chatId, "no handler")
                executeAsync(msg)
            }
        }
    }

    private fun processHandlers(update: Update) {
        if (!processCommandHandlers(update)
            && !processConversationHandlers(update)
            && !processTextHandlers(update)
        ) {
            throw MissingHandlerException()
        }
    }

    private fun processCommandHandlers(update: Update): Boolean {
        println("process command handlers")
        if (!update.message.isCommand) {
            return false
        }
        val handler = handlers.filterIsInstance<ICommandHandler>()
            .find { it.getFilter() == update.message.text } ?: return false

        conversationRepository.clearConversationState(
            update.message.chatId.toString(),
            update.message.from.id.toString()
        )
        handler.execute(this, update)
        return true
    }

    private fun processConversationHandlers(update: Update): Boolean {
        println("process conversations handlers")
        val conversationState = conversationRepository
            .getConversationState(
                chatId = update.message.chatId.toString(),
                telegramId = update.message.from.id.toString()
            ) ?: return false
        println("find conversations state: $conversationState")
        val handler = handlers.filterIsInstance<IConversationHandler>()
            .find { it.getConversationId() == conversationState.conversationId } ?: return false
        handler.execute(bot = this, update = update, state = conversationState.state)
        return true
    }

    private fun processTextHandlers(update: Update): Boolean {
        println("process text handlers")
        if (!update.message.hasText()) {
            return false
        }

        val handler = handlers.filterIsInstance<ITextHandler>()
            .find { it.getFilter() == update.message.text } ?: return false
        handler.execute(this, update)
        return true
    }
}