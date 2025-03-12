package org.example.study.service.cart_item.response

import org.example.study.domain.id.Ids

data class UpdateCartItemResponse(
    val cartId: Ids.CartId,
    val cartItemId: Ids.CartItemId,
    val cnt: Int
)
