package study.cart.out_msg

import study.persistence.Cart

data class CreateCartOutMessage(
    val cart: Cart
)
