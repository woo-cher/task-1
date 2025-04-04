package study.cart_item.dto

import study.type.id.Ids

data class DeleteCartItemsDto(
    val userId: Ids.UserId,
    val cartId: Ids.CartId,
    val cartItemIds: List<Ids.CartItemId>
)
