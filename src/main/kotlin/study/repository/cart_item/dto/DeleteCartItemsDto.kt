package org.example.study.repository.cart_item.dto

import org.example.study.domain.id.Ids

data class DeleteCartItemsDto(
    val userId: Ids.UserId,
    val cartId: Ids.CartId,
    val cartItemIds: List<Ids.CartItemId>
)
