package org.example.study.service

import org.example.study.domain.entity.Cart
import org.example.study.domain.id.Ids
import org.example.study.domain.policy.CartPolicy
import org.example.study.domain.policy.ExceptionThrower
import org.example.study.exception.CartAlreadyExistException
import org.example.study.exception.ExceptionHandler
import org.example.study.exception.errors.TaskErrors
import org.example.study.repository.CartRepository
import org.example.study.repository.ItemRepository
import org.example.study.repository.cart.dto.CreateCartDto
import org.example.study.repository.cart.dto.GetCartByUserDto
import org.example.study.repository.cart_item.dto.CreateCartItemDto
import org.example.study.repository.cart_item.dto.UpdateCartItemDto
import org.example.study.repository.item.dto.GetItemDto
import org.example.study.service.cart.request.CreateCartRequest
import org.example.study.service.cart.response.CreateCartResponse
import org.example.study.service.cart_item.request.CreateCartItemRequest
import org.example.study.service.cart_item.request.UpdateCartItemRequest
import org.example.study.service.cart_item.response.CreateCartItemResponse
import org.example.study.service.cart_item.response.UpdateCartItemResponse

// todo) 서비스 코드가 가지는 책임이 많아 분산해야 한다
@Deprecated("useCase 로 분리 후 제거")
class CartService(
    private val cartRepository: CartRepository,
    private val itemRepository: ItemRepository,
    private val cartPolicy: CartPolicy
) {
    fun create(req: CreateCartRequest): CreateCartResponse {
        return ExceptionHandler.handle {
            cartPolicy.validateExistsOrThrow(supplyCart(req.userId), alreadyCartExists)
            val created = cartRepository.createCart(req.toDto())
            CreateCartResponse(created.cart)
        }
    }

    fun createCartItem(req: CreateCartItemRequest): CreateCartItemResponse {
        return ExceptionHandler.handle {
            val item = getItem(req.itemId)
            val vo = cartRepository.createCartItem(req.toDto(item.price))
            CreateCartItemResponse(vo.cartId, vo.cartItemId, vo.cnt)
        }
    }

    fun updateCartItem(req: UpdateCartItemRequest): UpdateCartItemResponse {
        return ExceptionHandler.handle {
            val vo = cartRepository.updateCartItem(req.toDto())
            UpdateCartItemResponse(vo.cartId, vo.cartItemId, vo.cnt)
        }
    }

    private fun CreateCartRequest.toDto() = CreateCartDto(userId)
    private fun CreateCartItemRequest.toDto(price: Long) = CreateCartItemDto(userId, cartId, itemId, price, cnt)
    private fun UpdateCartItemRequest.toDto() = UpdateCartItemDto(userId, cartId, cartItemId, cnt)

    private fun supplyCart(userId: Ids.UserId): () -> Cart? = {
        cartRepository.findCartByUser(GetCartByUserDto(userId)).cart
    }
    private fun getItem(itemId: Ids.ItemId) = itemRepository.findById(GetItemDto(itemId))

    private val alreadyCartExists = ExceptionThrower<Ids.UserId> { userId ->
        val error = TaskErrors.CART_ALREADY_EXIST
        throw CartAlreadyExistException(error.code, error.messageWith(userId.id))
    }
}
