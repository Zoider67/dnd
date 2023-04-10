package com.zoider.dnd

import com.zoider.dnd.bot.PollingTgBot
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

@Component
class BotInitializingBean(val pollingTgBot: PollingTgBot): InitializingBean {
    override fun afterPropertiesSet() {
        val botsApi = TelegramBotsApi(DefaultBotSession::class.java)
        botsApi.registerBot(pollingTgBot)
    }
}