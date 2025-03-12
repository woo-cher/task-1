package org.example.study.repository.cart_item.dto

import org.example.study.domain.id.Ids

data class CreateCartItemDto(
    val cartId: Ids.CartId,
    val itemId: Ids.ItemId,
    val cnt: Int
)
