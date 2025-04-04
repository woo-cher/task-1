package study.cart_item.out_msg

import study.persistence.CartItem
import study.type.id.Ids

data class DeleteCartItemsOutMessage(
    val cartId: Ids.CartId,
    val cartItems: List<CartItem>
)
