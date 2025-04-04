package study.cart

import study.CartRepository
import study.CartUseCases
import study.cart.dto.GetCartByUserDto
import study.cart.in_msg.GetCartByUserInMessage
import study.cart.out_msg.GetCartByUserOutMessage
import study.cart.vo.GetCartByUserVo

class GetCartByUserIdUseCase(
    private val cartRepository: CartRepository
): CartUseCases.Get<GetCartByUserInMessage, GetCartByUserOutMessage> {

    override fun execute(inMsg: GetCartByUserInMessage): GetCartByUserOutMessage = inMsg.getCart().toOutMsg()

    private fun GetCartByUserInMessage.getCart(): GetCartByUserVo {
        val dto = GetCartByUserDto(userId)
        return cartRepository.findCartByUser(dto)
    }
    private fun GetCartByUserVo.toOutMsg() = GetCartByUserOutMessage(cart)
}
