package org.example.study.service

import org.example.study.domain.id.Ids
import org.example.study.domain.policy.CartPolicy
import org.example.study.exception.CartAlreadyExistException
import org.example.study.exception.errors.CartErrors
import org.example.study.repository.CartRepository
import org.example.study.repository.ItemRepository
import org.example.study.repository.cart.dto.CreateCartDto
import org.example.study.repository.cart.dto.GetCartByUserDto
import org.example.study.repository.cart_item.dto.CreateCartItemDto
import org.example.study.repository.cart_item.dto.DeleteCartItemsDto
import org.example.study.repository.cart_item.dto.UpdateCartItemDto
import org.example.study.repository.item.dto.GetItemDto
import org.example.study.service.cart.request.CreateCartRequest
import org.example.study.service.cart.request.GetCartByUserRequest
import org.example.study.service.cart.response.CreateCartResponse
import org.example.study.service.cart.response.GetCartByUserResponse
import org.example.study.service.cart_item.request.CreateCartItemRequest
import org.example.study.service.cart_item.request.DeleteCartItemsRequest
import org.example.study.service.cart_item.request.UpdateCartItemRequest
import org.example.study.service.cart_item.response.CreateCartItemResponse
import org.example.study.service.cart_item.response.DeleteCartItemResponse
import org.example.study.service.cart_item.response.UpdateCartItemResponse

class CartService(
    private val cartRepository: CartRepository,
    private val itemRepository: ItemRepository,
    private val cartPolicy: CartPolicy
) {
    fun create(req: CreateCartRequest): CreateCartResponse {
        val cartSupplier = { cartRepository.findCartByUser(GetCartByUserDto(req.userId)).cart }
        cartPolicy.canCreateCart(cartSupplier, this::alreadyCartExist)

        val created = cartRepository.createCart(req.toDto())
        return CreateCartResponse(created.cart)
    }

    fun createCartItem(req: CreateCartItemRequest): CreateCartItemResponse {
        val item = getItem(req.itemId)
        val vo = cartRepository.createCartItem(req.toDto(item.price))
        return CreateCartItemResponse(vo.cartId, vo.cartItemId, vo.cnt)
    }

    fun deleteCartItems(req: DeleteCartItemsRequest): DeleteCartItemResponse {
        val vo = cartRepository.deleteCartItems(req.toDto())
        return DeleteCartItemResponse(vo.cartIds, vo.cartItems)
    }

    fun updateCartItem(req: UpdateCartItemRequest): UpdateCartItemResponse {
        val vo = cartRepository.updateCartItem(req.toDto())
        return UpdateCartItemResponse(vo.cartId, vo.cartItemId, vo.cnt)
    }

    fun getCartByUser(req: GetCartByUserRequest): GetCartByUserResponse {
        val vo = cartRepository.findCartByUser(req.toDto())
        return GetCartByUserResponse(vo.cart)
    }

    private fun CreateCartRequest.toDto() = CreateCartDto(userId)
    private fun CreateCartItemRequest.toDto(price: Long) = CreateCartItemDto(userId, cartId, itemId, price, cnt)
    private fun DeleteCartItemsRequest.toDto() = DeleteCartItemsDto(userId, cartId, cartItemIds)
    private fun UpdateCartItemRequest.toDto() = UpdateCartItemDto(userId, cartId, cartItemId, cnt)
    private fun GetCartByUserRequest.toDto() = GetCartByUserDto(userId)

    private fun getItem(itemId: Ids.ItemId) = itemRepository.findById(GetItemDto(itemId))

    private fun alreadyCartExist(userId: Ids.UserId) {
        throw CartAlreadyExistException(CartErrors.CART_ALREADY_EXIST.code, userId.id)
    }
}
