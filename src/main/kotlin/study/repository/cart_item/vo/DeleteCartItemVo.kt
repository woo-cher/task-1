package org.example.study.repository.cart_item.vo

import org.example.study.domain.entity.CartItem
import org.example.study.domain.id.Ids

data class DeleteCartItemVo(
    val cartId: Ids.CartId,
    val cartItems: List<CartItem>
)
