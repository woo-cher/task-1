package org.example.study.repository.vo

import org.example.study.domain.id.Ids

data class CreateCartItemVo(
    val cartId: Ids.CartId,
    val itemId: Ids.ItemId,
    val cnt: Int
)
