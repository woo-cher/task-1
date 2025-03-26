package org.example.study.usecase.cart_item.out_msg

import org.example.study.domain.id.Ids

data class UpdateCartItemOutMessage(
    val cartId: Ids.CartId,
    val cartItemId: Ids.CartItemId,
    val cnt: Int
)
