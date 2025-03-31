package org.example.study.usecase.cart_item

import org.example.study.exception.ExceptionHandler
import org.example.study.repository.CartRepository
import org.example.study.repository.cart_item.dto.UpdateCartItemDto
import org.example.study.repository.cart_item.vo.UpdateCartItemVo
import org.example.study.usecase.CartItemUseCases
import org.example.study.usecase.cart_item.in_msg.UpdateCartItemInMessage
import org.example.study.usecase.cart_item.out_msg.UpdateCartItemOutMessage

class UpdateCartItemUseCase(
    private val cartRepository: CartRepository
): CartItemUseCases.Update<UpdateCartItemInMessage, UpdateCartItemOutMessage> {

    override fun update(inMsg: UpdateCartItemInMessage): UpdateCartItemOutMessage = ExceptionHandler.handle {
        cartRepository.updateCartItem(inMsg.toDto()).toOutMsg()
    }

    private fun UpdateCartItemInMessage.toDto() = UpdateCartItemDto(userId, cartId, cartItemId, cnt)
    private fun UpdateCartItemVo.toOutMsg() = UpdateCartItemOutMessage(cartId, cartItemId, cnt)
}
