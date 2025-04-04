package study.order

import study.CartRepository
import study.OrderRepository
import study.persistence.CartItem
import study.OrderUseCases
import study.cart_item.dto.DeleteCartItemsDto
import study.order.dto.CreateOrderDto
import study.order.in_msg.CreateOrderInMessage
import study.order.out_msg.CreateOrderOutMessage
import study.order.vo.CreateOrderVo

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
