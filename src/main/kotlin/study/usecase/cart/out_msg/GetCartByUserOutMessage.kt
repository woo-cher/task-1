package org.example.study.usecase.cart.out_msg

import org.example.study.domain.entity.Cart

data class GetCartByUserOutMessage(
    val cart: Cart?
)
