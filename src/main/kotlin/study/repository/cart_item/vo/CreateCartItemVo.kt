package org.example.study.repository.cart_item.vo

import org.example.study.domain.id.Ids

data class CreateCartItemVo(
    val cartId: Ids.CartId,
    val cartItemId: Ids.CartItemId,
    val cnt: Int
)
