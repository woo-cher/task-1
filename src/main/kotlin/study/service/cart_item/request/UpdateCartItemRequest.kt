package org.example.study.service.cart_item.request

import org.example.study.domain.id.Ids

data class UpdateCartItemRequest(
    val userId: Ids.UserId,
    val cartId: Ids.CartId,
    val cartItemId: Ids.CartItemId,
    val cnt: Int
)
