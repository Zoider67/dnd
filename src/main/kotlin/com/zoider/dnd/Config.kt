package com.zoider.dnd

import com.zoider.dnd.bot.PollingTgBot
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(basePackageClasses = [PollingTgBot::class])
class Config {
    @Bean
    fun getTgToken(): String {
        return PollingTgBot.TG_TOKEN
    }
}