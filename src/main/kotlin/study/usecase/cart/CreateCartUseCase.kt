package org.example.study.usecase.cart

import org.example.study.domain.entity.Cart
import org.example.study.domain.id.Ids
import org.example.study.domain.policy.CartPolicy
import org.example.study.domain.policy.ExceptionThrower
import org.example.study.exception.CartAlreadyExistException
import org.example.study.exception.ExceptionHandler
import org.example.study.exception.errors.TaskErrors
import org.example.study.repository.CartRepository
import org.example.study.repository.cart.dto.CreateCartDto
import org.example.study.repository.cart.dto.GetCartByUserDto
import org.example.study.repository.cart.vo.CreateCartVo
import org.example.study.usecase.CartUseCases
import org.example.study.usecase.cart.in_msg.CreateCartInMessage
import org.example.study.usecase.cart.out_msg.CreateCartOutMessage

class CreateCartUseCase(
    private val cartRepository: CartRepository,
    private val cartPolicy: CartPolicy,
): CartUseCases.CreateCartUseCase<CreateCartInMessage, CreateCartOutMessage> {

    override fun create(inMsg: CreateCartInMessage): CreateCartOutMessage {
        return ExceptionHandler.handle {
            cartPolicy.validateExistsOrThrow(supplyCart(inMsg.userId), alreadyCartExists)
            cartRepository.createCart(inMsg.toDto()).toOutMsg()
        }
    }

    private fun CreateCartInMessage.toDto() = CreateCartDto(userId)
    private fun supplyCart(userId: Ids.UserId): () -> Cart? = {
        cartRepository.findCartByUser(GetCartByUserDto(userId)).cart
    }
    private val alreadyCartExists = ExceptionThrower<Ids.UserId> { userId ->
        val error = TaskErrors.CART_ALREADY_EXIST
        throw CartAlreadyExistException(error.code, error.messageWith(userId.id))
    }
    private fun CreateCartVo.toOutMsg() = CreateCartOutMessage(cart)
}
