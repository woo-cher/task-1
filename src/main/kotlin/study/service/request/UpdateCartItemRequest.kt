package org.example.study.service.request

import org.example.study.domain.id.Ids

data class UpdateCartItemRequest(
    val cartId: Ids.CartId,
    val cartItemId: Ids.CartItemId,
    val cnt: Int
)
