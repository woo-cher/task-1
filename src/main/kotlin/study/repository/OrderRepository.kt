package org.example.study.repository

import org.example.study.domain.entity.Order
import org.example.study.domain.enums.OrderStatus
import org.example.study.domain.id.Ids
import org.example.study.repository.order.dto.CreateOrderDto
import org.example.study.repository.order.vo.CreateOrderVo

class OrderRepository(
    private var orders: MutableMap<Ids.OrderId, Order> = mutableMapOf(),
    private var orderNum: Long = 1L
) {
    fun createOrder(dto: CreateOrderDto): CreateOrderVo {
        val orderId = Ids.OrderId(orderNum)
        orderNum = Ids.autoIncrement(orderNum)

        val created = Order(orderId, dto.userId, dto.cartItems, OrderStatus.PROCESSED, dto.price)
        orders[orderId] = created

        return CreateOrderVo(orderId, created.userId, created.cartItems, created.status, created.price)
    }
}
