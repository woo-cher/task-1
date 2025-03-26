package org.example.study.usecase.cart_item.in_msg

import org.example.study.domain.id.Ids

data class DeleteCartItemsInMessage(
    val userId: Ids.UserId,
    val cartId: Ids.CartId,
    val cartItemIds: List<Ids.CartItemId>
)
