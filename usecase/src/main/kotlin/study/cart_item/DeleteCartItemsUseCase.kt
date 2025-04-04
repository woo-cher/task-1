package study.cart_item

import study.CartItemUseCases
import study.CartRepository
import study.cart_item.dto.DeleteCartItemsDto
import study.cart_item.in_msg.DeleteCartItemsInMessage
import study.cart_item.out_msg.DeleteCartItemsOutMessage
import study.cart_item.vo.DeleteCartItemsVo

class DeleteCartItemsUseCase(
    private val cartRepository: CartRepository
): CartItemUseCases.Delete<DeleteCartItemsInMessage, DeleteCartItemsOutMessage> {

    override fun execute(inMsg: DeleteCartItemsInMessage): DeleteCartItemsOutMessage = cartRepository.deleteCartItems(inMsg.toDto()).toOutMsg()

    private fun DeleteCartItemsInMessage.toDto() = DeleteCartItemsDto(userId, cartId, cartItemIds)
    private fun DeleteCartItemsVo.toOutMsg() = DeleteCartItemsOutMessage(cartId, cartItems)
}
