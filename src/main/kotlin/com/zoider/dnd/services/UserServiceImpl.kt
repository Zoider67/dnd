package com.zoider.dnd.services

import com.zoider.dnd.models.TgUser
import com.zoider.dnd.repositories.TgUserRepository
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val tgUserRepository: TgUserRepository
) : IUserService {
    override fun saveTelegramUser(tgUserId: String) {
        val user = tgUserRepository.getTgUserByTelegramId(tgUserId)
        if (user == null) {
            tgUserRepository.saveTgUser(
                TgUser(
                    telegramId = tgUserId
                )
            )
        }
    }
}