package study.cart_item

import study.CartItemUseCases
import study.CartRepository
import study.cart_item.dto.UpdateCartItemDto
import study.cart_item.in_msg.UpdateCartItemInMessage
import study.cart_item.out_msg.UpdateCartItemOutMessage
import study.cart_item.vo.UpdateCartItemVo

class UpdateCartItemUseCase(
    private val cartRepository: CartRepository
): CartItemUseCases.Update<UpdateCartItemInMessage, UpdateCartItemOutMessage> {

    override fun execute(inMsg: UpdateCartItemInMessage): UpdateCartItemOutMessage = cartRepository.updateCartItem(inMsg.toDto()).toOutMsg()

    private fun UpdateCartItemInMessage.toDto() = UpdateCartItemDto(userId, cartId, cartItemId, cnt)
    private fun UpdateCartItemVo.toOutMsg() = UpdateCartItemOutMessage(cartId, cartItemId, cnt)
}
