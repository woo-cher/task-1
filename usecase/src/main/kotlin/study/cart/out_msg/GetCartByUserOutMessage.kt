package study.cart.out_msg

import study.type.data.CartData

data class GetCartByUserOutMessage(
    val cart: CartData?
)
