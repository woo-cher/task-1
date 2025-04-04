package study.cart_item.dto

import study.type.id.Ids

data class UpdateCartItemDto(
    val userId: Ids.UserId,
    val cartId: Ids.CartId,
    val cartItemId: Ids.CartItemId,
    val cnt: Int
)
