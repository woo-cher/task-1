package study.order.in_msg

import study.persistence.CartItem
import study.type.id.Ids

data class CreateOrderInMessage(
    val userId: Ids.UserId,
    val cartId: Ids.CartId,
    val cartItems: List<CartItem>,
)
