package org.example.study.usecase.order.in_msg

import org.example.study.domain.entity.CartItem
import org.example.study.domain.id.Ids

data class CreateOrderInMessage(
    val userId: Ids.UserId,
    val cartId: Ids.CartId,
    val cartItems: List<CartItem>,
)
