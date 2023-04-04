package com.zoider.dnd.services

import com.zoider.dnd.models.Character
import com.zoider.dnd.models.Item

interface ItemService {

    fun showCharacterInventory(userId: String)

    fun addItemToInventory(userId: String, item: Item, amount: Int)

    fun removeItemFromInventory(userId: String, item: Item, amount: Int)
}