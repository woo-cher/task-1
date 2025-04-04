package study.persistence

import study.type.id.Ids

data class Cart(
    val cartId: Ids.CartId,
    val cartItems: MutableList<CartItem>,
    val userId: Ids.UserId
)
