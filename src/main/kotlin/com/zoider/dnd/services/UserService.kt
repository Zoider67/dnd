package com.zoider.dnd.services

import com.zoider.dnd.models.TgUser

interface UserService {

    fun saveUser(tgUser: TgUser): TgUser
}