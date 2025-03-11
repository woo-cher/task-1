package org.example.study.repository.dto

import org.example.study.domain.id.Ids

data class DeleteCartItemDto(
    val cartId: Ids.CartId,
    val cartItemId: Ids.CartItemId
)
