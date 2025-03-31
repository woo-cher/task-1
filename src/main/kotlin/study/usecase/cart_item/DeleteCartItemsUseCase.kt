package org.example.study.usecase.cart_item

import org.example.study.exception.ExceptionHandler
import org.example.study.repository.CartRepository
import org.example.study.repository.cart_item.dto.DeleteCartItemsDto
import org.example.study.repository.cart_item.vo.DeleteCartItemsVo
import org.example.study.usecase.CartItemUseCases
import org.example.study.usecase.cart_item.in_msg.DeleteCartItemsInMessage
import org.example.study.usecase.cart_item.out_msg.DeleteCartItemsOutMessage

class DeleteCartItemsUseCase(
    private val cartRepository: CartRepository
): CartItemUseCases.Delete<DeleteCartItemsInMessage, DeleteCartItemsOutMessage> {

    override fun delete(inMsg: DeleteCartItemsInMessage): DeleteCartItemsOutMessage = ExceptionHandler.handle {
        cartRepository.deleteCartItems(inMsg.toDto()).toOutMsg()
    }

    private fun DeleteCartItemsInMessage.toDto() = DeleteCartItemsDto(userId, cartId, cartItemIds)
    private fun DeleteCartItemsVo.toOutMsg() = DeleteCartItemsOutMessage(cartId, cartItems)
}
