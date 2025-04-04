package study.order.out_msg

import study.persistence.CartItem
import study.type.enums.OrderStatus
import study.type.id.Ids

data class CreateOrderOutMessage(
    val orderId: Ids.OrderId,
    val userId: Ids.UserId,
    val cartItems: List<CartItem>,
    val status: OrderStatus,
    val price: Long
)
