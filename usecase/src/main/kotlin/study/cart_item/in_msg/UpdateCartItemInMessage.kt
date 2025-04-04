package study.cart_item.in_msg

import study.type.id.Ids

data class UpdateCartItemInMessage(
    val userId: Ids.UserId,
    val cartId: Ids.CartId,
    val cartItemId: Ids.CartItemId,
    val cnt: Int
)
