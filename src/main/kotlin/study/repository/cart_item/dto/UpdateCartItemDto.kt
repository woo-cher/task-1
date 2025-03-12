package org.example.study.repository.cart_item.dto

import org.example.study.domain.id.Ids

data class UpdateCartItemDto(
    val cartId: Ids.CartId,
    val cartItemId: Ids.CartItemId,
    val cnt: Int
)
