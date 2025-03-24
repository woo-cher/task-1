package org.example.study.service

import org.example.study.domain.entity.CartItem
import org.example.study.exception.ExceptionHandler
import org.example.study.repository.CartRepository
import org.example.study.repository.OrderRepository
import org.example.study.repository.cart_item.dto.DeleteCartItemsDto
import org.example.study.repository.order.dto.CreateOrderDto
import org.example.study.service.order.request.CreateOrderRequest
import org.example.study.service.order.response.CreateOrderResponse

class OrderService(
    private val orderRepository: OrderRepository,
    private val cartRepository: CartRepository
) {
    fun create(req: CreateOrderRequest): CreateOrderResponse {
        return ExceptionHandler.handle {
            val created = orderRepository.createOrder(req.toDto())
            req.deleteCartItems()
            CreateOrderResponse(created.orderId, created.userId, created.cartItems, created.status, created.price)
        }
    }

    private fun CreateOrderRequest.toDto() = CreateOrderDto(userId, cartItems, calculatePrice(cartItems))
    private fun calculatePrice(cartItems: List<CartItem>) = cartItems.sumOf { it.price * it.cnt }
    private fun CreateOrderRequest.deleteCartItems() {
        val cartItemIds = cartItems.map { it.cartItemId }.toList()
        cartRepository.deleteCartItems(DeleteCartItemsDto(userId, cartId, cartItemIds))
    }
}
