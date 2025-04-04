package study.cart_item.in_msg

import study.type.id.Ids

data class DeleteCartItemsInMessage(
    val userId: Ids.UserId,
    val cartId: Ids.CartId,
    val cartItemIds: List<Ids.CartItemId>
)
