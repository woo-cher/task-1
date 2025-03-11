package org.example.study.domain.entity

import org.example.study.domain.id.Ids

class CartItem(
    val cartItemId: Ids.CartItemId,
    val cartId: Ids.CartId,
    val itemId: Ids.ItemId,
    var cnt: Int
)
