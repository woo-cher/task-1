package org.example.study.service.cart.response

import org.example.study.domain.entity.Cart

data class GetCartByUserResponse(
    val cart: Cart?
)
