package org.example.study.service.cart_item.request

import org.example.study.domain.id.Ids

data class DeleteCartItemsRequest(
    val cartId: Ids.CartId,
    val cartItemIds: List<Ids.CartItemId>
)
