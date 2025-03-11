package org.example.study.repository.dto

import org.example.study.domain.id.Ids

data class UpdateCartItemDto(
    val cartId: Ids.CartId,
    val cartItemId: Ids.CartItemId,
    val cnt: Int
)
