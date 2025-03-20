package org.example.study.service

import org.example.study.domain.id.Ids
import org.example.study.repository.CartRepository
import org.example.study.repository.ItemRepository
import org.example.study.repository.cart.dto.CreateCartDto
import org.example.study.repository.cart_item.dto.CreateCartItemDto
import org.example.study.repository.cart_item.dto.DeleteCartItemsDto
import org.example.study.repository.cart_item.dto.UpdateCartItemDto
import org.example.study.repository.item.dto.GetItemDto
import org.example.study.service.cart.request.CreateCartRequest
import org.example.study.service.cart.response.CreateCartResponse
import org.example.study.service.cart_item.request.CreateCartItemRequest
import org.example.study.service.cart_item.request.DeleteCartItemsRequest
import org.example.study.service.cart_item.request.UpdateCartItemRequest
import org.example.study.service.cart_item.response.CreateCartItemResponse
import org.example.study.service.cart_item.response.DeleteCartItemResponse
import org.example.study.service.cart_item.response.UpdateCartItemResponse

class CartService(
    private val cartRepository: CartRepository,
    private val itemRepository: ItemRepository
) {

    fun create(req: CreateCartRequest): CreateCartResponse {
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

    private fun CreateCartRequest.toDto() = CreateCartDto(userId)
    private fun CreateCartItemRequest.toDto(price: Long) = CreateCartItemDto(cartId, itemId, price, cnt)
    private fun DeleteCartItemsRequest.toDto() = DeleteCartItemsDto(cartId, cartItemIds)
    private fun UpdateCartItemRequest.toDto() = UpdateCartItemDto(cartId, cartItemId, cnt)
    private fun getItem(itemId: Ids.ItemId) = itemRepository.findById(GetItemDto(itemId))
}
