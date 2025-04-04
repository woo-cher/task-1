package study.persistence

import study.type.data.CartData
import study.type.id.Ids

data class Cart(
    val cartId: Ids.CartId,
    val cartItems: MutableList<CartItem>,
    val userId: Ids.UserId
): Persistence<CartData> {
    override fun toData(): CartData = CartData(cartId, cartItems.mapToData(), userId)
}
