package org.example.study.domain.entity

import org.example.study.domain.id.Ids

data class Cart(
    val cartId: Ids.CartId,
    val cartItems: MutableList<CartItem>
)
