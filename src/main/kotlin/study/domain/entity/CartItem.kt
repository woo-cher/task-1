package org.example.study.domain.entity

import org.example.study.domain.id.Ids

class CartItem(
    val id: Ids.CartItemId,
    val cartId: Ids.CartId,
    val itemId: Ids.ItemId,
)
