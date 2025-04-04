package study.order.dto

import study.persistence.CartItem
import study.type.id.Ids

data class CreateOrderDto(
    val userId: Ids.UserId,
    val cartItems: List<CartItem>,
    val price: Long,
)
