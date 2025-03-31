package org.example.study.usecase.cart_item

import org.example.study.repository.CartRepository
import org.example.study.repository.cart_item.dto.DeleteCartItemsDto
import org.example.study.repository.cart_item.vo.DeleteCartItemsVo
import org.example.study.usecase.CartItemUseCases
import org.example.study.usecase.cart_item.in_msg.DeleteCartItemsInMessage
import org.example.study.usecase.cart_item.out_msg.DeleteCartItemsOutMessage

class DeleteCartItemsUseCase(
    private val cartRepository: CartRepository
): CartItemUseCases.Delete<DeleteCartItemsInMessage, DeleteCartItemsOutMessage> {

    override fun execute(inMsg: DeleteCartItemsInMessage): DeleteCartItemsOutMessage = cartRepository.deleteCartItems(inMsg.toDto()).toOutMsg()

    private fun DeleteCartItemsInMessage.toDto() = DeleteCartItemsDto(userId, cartId, cartItemIds)
    private fun DeleteCartItemsVo.toOutMsg() = DeleteCartItemsOutMessage(cartId, cartItems)
}
