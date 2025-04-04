package study.type.data

import study.type.InfraDataType
import study.type.enums.OrderStatus
import study.type.id.Ids

data class OrderData(
    val orderId: Ids.OrderId,
    val userId: Ids.UserId,
    val cartItems: List<CartItemData>,
    val status: OrderStatus,
    val price: Long
): InfraDataType
