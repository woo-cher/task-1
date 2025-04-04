package study.type.data

import study.type.InfraDataType
import study.type.id.Ids

data class CartData(
    val cartId: Ids.CartId,
    val cartItems: List<CartItemData>,
    val userId: Ids.UserId
): InfraDataType
