package org.example.study.repository.cart_item.dto

import org.example.study.domain.id.Ids

data class DeleteCartItemDto(
    val cartId: Ids.CartId,
    val cartItemId: Ids.CartItemId
)
