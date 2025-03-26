package org.example.study.usecase.cart_item.in_msg

import org.example.study.domain.id.Ids

data class UpdateCartItemInMessage(
    val userId: Ids.UserId,
    val cartId: Ids.CartId,
    val cartItemId: Ids.CartItemId,
    val cnt: Int
)
