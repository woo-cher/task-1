package study.cart_item.in_msg

import study.type.id.Ids

data class CreateCartItemInMessage(
    val userId: Ids.UserId,
    val cartId: Ids.CartId,
    val itemId: Ids.ItemId,
    val cnt: Int
)
