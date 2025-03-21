package org.example.study.service.cart_item.request

import org.example.study.domain.id.Ids

data class CreateCartItemRequest(
    val userId: Ids.UserId,
    val cartId: Ids.CartId,
    val itemId: Ids.ItemId,
    val cnt: Int
)
