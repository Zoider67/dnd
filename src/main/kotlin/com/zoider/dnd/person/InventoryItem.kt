package com.zoider.dnd.person

import com.zoider.dnd.item.Item
import javax.persistence.*

@Entity
@IdClass(InventoryItemId::class)
class InventoryItem(
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    var person: Person,
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    var item: Item,
    var amount: Integer
)