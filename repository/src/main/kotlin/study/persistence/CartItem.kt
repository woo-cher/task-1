package study.persistence

import study.type.data.CartItemData
import study.type.enums.ShippingStatus
import study.type.id.Ids

data class CartItem(
    val cartItemId: Ids.CartItemId,
    val cartId: Ids.CartId,
    val itemId: Ids.ItemId,
    val price: Long,
    var cnt: Int,
    val status: ShippingStatus
): Persistence<CartItemData> {
    override fun toData() = CartItemData(cartItemId, cartId, itemId, price, cnt, status)
}
