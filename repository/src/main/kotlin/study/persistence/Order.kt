package study.persistence

import study.type.enums.OrderStatus
import study.type.id.Ids

data class Order(
    val orderId: Ids.OrderId,
    val userId: Ids.UserId,
    val cartItems: List<CartItem>,
    val status: OrderStatus,
    val price: Long
)
