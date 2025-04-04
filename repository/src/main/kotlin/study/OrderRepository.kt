package study

import study.order.dto.CreateOrderDto
import study.order.vo.CreateOrderVo
import study.persistence.Order
import study.type.enums.OrderStatus
import study.type.id.Ids

class OrderRepository(
    private var orders: MutableMap<Ids.OrderId, Order> = mutableMapOf(),
    private var orderNum: Long = Generator.startId()
) {
    fun createOrder(dto: CreateOrderDto): CreateOrderVo {
        val orderId = Ids.OrderId(orderNum)
        orderNum = Ids.autoIncrement(orderNum)

        val created = Order(orderId, dto.userId, dto.cartItems, OrderStatus.PROCESSED, dto.price)
        orders[orderId] = created

        return CreateOrderVo(orderId, created.userId, created.cartItems, created.status, created.price)
    }
}
