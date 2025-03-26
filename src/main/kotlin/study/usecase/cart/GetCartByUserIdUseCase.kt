package org.example.study.usecase.cart

import org.example.study.repository.CartRepository
import org.example.study.repository.cart.dto.GetCartByUserDto
import org.example.study.repository.cart.vo.GetCartByUserVo
import org.example.study.usecase.CartUseCases
import org.example.study.usecase.cart.in_msg.GetCartByUserInMessage
import org.example.study.usecase.cart.out_msg.GetCartByUserOutMessage

class GetCartByUserIdUseCase(
    private val cartRepository: CartRepository
): CartUseCases.GetCartUseCase<GetCartByUserInMessage, GetCartByUserOutMessage> {

    override fun get(inMsg: GetCartByUserInMessage): GetCartByUserOutMessage = inMsg.getCart().toOutMsg()

    private fun GetCartByUserInMessage.getCart(): GetCartByUserVo {
        val dto = GetCartByUserDto(userId)
        return cartRepository.findCartByUser(dto)
    }
    private fun GetCartByUserVo.toOutMsg() = GetCartByUserOutMessage(cart)
}
