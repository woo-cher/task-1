package study.cart_item.vo

import study.type.id.Ids

data class CreateCartItemVo(
    val cartId: Ids.CartId,
    val cartItemId: Ids.CartItemId,
    val cnt: Int
)
