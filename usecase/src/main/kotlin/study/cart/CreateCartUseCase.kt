package study.cart

import study.CartRepository
import study.CartUseCases
import study.cart.dto.CreateCartDto
import study.cart.dto.GetCartByUserDto
import study.cart.in_msg.CreateCartInMessage
import study.cart.out_msg.CreateCartOutMessage
import study.cart.vo.CreateCartVo
import study.persistence.Cart
import study.policy.CartPolicy
import study.policy.ExceptionThrower
import study.type.exception.CartAlreadyExistException
import study.type.exception.errors.TaskErrors
import study.type.id.Ids

class CreateCartUseCase(
    private val cartRepository: CartRepository,
    private val cartPolicy: CartPolicy,
): CartUseCases.Create<CreateCartInMessage, CreateCartOutMessage> {

    override fun execute(inMsg: CreateCartInMessage): CreateCartOutMessage {
        cartPolicy.validateExistsOrThrow(supplyCart(inMsg.userId), alreadyCartExists)
        return cartRepository.createCart(inMsg.toDto()).toOutMsg()
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
