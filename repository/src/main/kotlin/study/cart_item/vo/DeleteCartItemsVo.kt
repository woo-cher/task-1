package study.cart_item.vo

import study.persistence.CartItem
import study.type.id.Ids

data class DeleteCartItemsVo(
    val cartId: Ids.CartId,
    val cartItems: List<CartItem>
)
