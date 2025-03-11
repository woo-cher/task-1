package org.example.study.service.reponse

import org.example.study.domain.id.Ids

data class UpdateCartItemResponse(
    val cartId: Ids.CartId,
    val cartItemId: Ids.CartItemId,
    val cnt: Int
)
