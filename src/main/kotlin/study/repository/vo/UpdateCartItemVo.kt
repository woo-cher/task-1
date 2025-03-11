package org.example.study.repository.vo

import org.example.study.domain.id.Ids

data class UpdateCartItemVo(
    val cartId: Ids.CartId,
    val cartItemId: Ids.CartItemId,
    val cnt: Int
)
