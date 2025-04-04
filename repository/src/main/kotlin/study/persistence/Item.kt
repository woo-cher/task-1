package study.persistence

import study.type.id.Ids

data class Item(
    val itemId: Ids.ItemId,
    val name: String,
    val price: Long,
    val fee: Long
)
