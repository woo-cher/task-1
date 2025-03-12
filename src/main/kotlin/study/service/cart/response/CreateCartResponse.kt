package org.example.study.service.cart.response

import org.example.study.domain.entity.CartItem
import org.example.study.domain.id.Ids

data class CreateCartResponse(
    val cartId: Ids.CartId,
    val cartItems: List<CartItem>,
    val userId: Ids.UserId
)
