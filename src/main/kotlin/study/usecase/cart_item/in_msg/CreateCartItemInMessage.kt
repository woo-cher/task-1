package org.example.study.usecase.cart_item.in_msg

import org.example.study.domain.id.Ids

data class CreateCartItemInMessage(
    val userId: Ids.UserId,
    val cartId: Ids.CartId,
    val itemId: Ids.ItemId,
    val cnt: Int
)
