package study.type.data

import study.type.InfraDataType
import study.type.enums.ShippingStatus
import study.type.id.Ids

data class CartItemData(
    val cartItemId: Ids.CartItemId,
    val cartId: Ids.CartId,
    val itemId: Ids.ItemId,
    val price: Long,
    var cnt: Int,
    val status: ShippingStatus
): InfraDataType
