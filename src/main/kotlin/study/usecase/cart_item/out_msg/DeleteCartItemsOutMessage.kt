package org.example.study.usecase.cart_item.out_msg

import org.example.study.domain.entity.CartItem
import org.example.study.domain.id.Ids

data class DeleteCartItemsOutMessage(
    val cartId: Ids.CartId,
    val cartItems: List<CartItem>
)
