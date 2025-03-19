package org.example.study.repository.order.dto

import org.example.study.domain.entity.CartItem
import org.example.study.domain.enums.OrderStatus
import org.example.study.domain.id.Ids

data class CreateOrderDto(
    val userId: Ids.UserId,
    val cartItems: List<CartItem>,
    val status: OrderStatus = OrderStatus.PROCESSED
)
