package org.example.study.repository.cart_item.dto

import org.example.study.domain.id.Ids

data class CreateCartItemDto(
    val userId: Ids.UserId,
    val cartId: Ids.CartId,
    val itemId: Ids.ItemId,
    val price: Long,
    val cnt: Int
)
