package study.cart_item.out_msg

import study.type.id.Ids

data class UpdateCartItemOutMessage(
    val cartId: Ids.CartId,
    val cartItemId: Ids.CartItemId,
    val cnt: Int
)
