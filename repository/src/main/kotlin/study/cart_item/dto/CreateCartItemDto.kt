package study.cart_item.dto

import study.type.id.Ids

data class CreateCartItemDto(
    val userId: Ids.UserId,
    val cartId: Ids.CartId,
    val itemId: Ids.ItemId,
    val price: Long,
    val cnt: Int
)
