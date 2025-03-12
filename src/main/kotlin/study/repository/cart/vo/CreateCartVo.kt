package org.example.study.repository.cart.vo

import org.example.study.domain.entity.CartItem
import org.example.study.domain.id.Ids

data class CreateCartVo(
    val cartId: Ids.CartId,
    val cartItems: List<CartItem>,
    val userId: Ids.UserId
)
