package org.example.study.usecase.order

import org.example.study.domain.entity.CartItem
import org.example.study.repository.CartRepository
import org.example.study.repository.OrderRepository
import org.example.study.repository.cart_item.dto.DeleteCartItemsDto
import org.example.study.repository.order.dto.CreateOrderDto
import org.example.study.repository.order.vo.CreateOrderVo
import org.example.study.usecase.OrderUseCases
import org.example.study.usecase.order.in_msg.CreateOrderInMessage
import org.example.study.usecase.order.out_msg.CreateOrderOutMessage

class CreateOrderUseCase(
    private val orderRepository: OrderRepository,
    private val cartRepository: CartRepository
): OrderUseCases.Create<CreateOrderInMessage, CreateOrderOutMessage> {

    override fun execute(inMsg: CreateOrderInMessage): CreateOrderOutMessage {
        val created = orderRepository.createOrder(inMsg.toDto())
        inMsg.deleteCartItems()
        return created.toOutMsg()
    }

    private fun CreateOrderInMessage.toDto() = CreateOrderDto(userId, cartItems, calculatePrice(cartItems))
    private fun calculatePrice(cartItems: List<CartItem>) = cartItems.sumOf { it.price * it.cnt }
    private fun CreateOrderInMessage.deleteCartItems() {
        val cartItemIds = cartItems.map { it.cartItemId }.toList()
        cartRepository.deleteCartItems(DeleteCartItemsDto(userId, cartId, cartItemIds))
    }
    private fun CreateOrderVo.toOutMsg() = CreateOrderOutMessage(orderId, userId, cartItems, status, price)
}
