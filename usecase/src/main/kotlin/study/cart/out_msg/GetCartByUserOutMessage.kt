package study.cart.out_msg

import study.persistence.Cart

data class GetCartByUserOutMessage(
    val cart: Cart?
)
