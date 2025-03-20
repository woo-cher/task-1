package org.example.study.service.order.request

import org.example.study.domain.entity.CartItem
import org.example.study.domain.id.Ids

data class CreateOrderRequest(
    val userId: Ids.UserId,
    val cartId: Ids.CartId,
    val cartItems: List<CartItem>,
)
