package org.example.study.service

import org.example.study.domain.entity.CartItem
import org.example.study.repository.OrderRepository
import org.example.study.repository.order.dto.CreateOrderDto
import org.example.study.service.order.request.CreateOrderRequest
import org.example.study.service.order.response.CreateOrderResponse

class OrderService(
    private val orderRepository: OrderRepository
) {
    fun create(request: CreateOrderRequest): CreateOrderResponse {
        val dto = CreateOrderDto(request.userId, request.cartItems, calculatePrice(request.cartItems))
        val created = orderRepository.createOrder(dto)
        return CreateOrderResponse(created.orderId, created.userId, created.cartItems, created.status, created.price)
    }

    private fun calculatePrice(cartItems: List<CartItem>) = cartItems.sumOf { it.price }
}
