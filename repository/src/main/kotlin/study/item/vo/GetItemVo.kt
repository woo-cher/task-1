package study.item.vo

import study.type.id.Ids

data class GetItemVo(
    val itemId: Ids.ItemId,
    val name: String,
    val price: Long,
    val fee: Long
)
