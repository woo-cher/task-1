package org.example.study.domain.entity

import org.example.study.domain.enums.OrderStatus
import org.example.study.domain.id.Ids

data class Order(
    val orderId: Ids.OrderId,
    val userId: Ids.UserId,
    val cartItems: List<CartItem>,
    val status: OrderStatus,
    val price: Long
) {
    fun calculate(): Long {
        TODO("calculate price")
    }
}
