package org.example.study.service.cart_item.response

import org.example.study.domain.entity.CartItem
import org.example.study.domain.id.Ids

data class DeleteCartItemResponse(
    val cartId: Ids.CartId,
    val cartItems: List<CartItem>
)
