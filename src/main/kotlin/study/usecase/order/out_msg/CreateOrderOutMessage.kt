package org.example.study.usecase.order.out_msg

import org.example.study.domain.entity.CartItem
import org.example.study.domain.enums.OrderStatus
import org.example.study.domain.id.Ids

data class CreateOrderOutMessage(
    val orderId: Ids.OrderId,
    val userId: Ids.UserId,
    val cartItems: List<CartItem>,
    val status: OrderStatus,
    val price: Long
)
