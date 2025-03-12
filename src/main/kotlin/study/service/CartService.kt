package org.example.study.service

import org.example.study.repository.CartRepository
import org.example.study.repository.cart.dto.CreateCartDto
import org.example.study.repository.cart_item.dto.CreateCartItemDto
import org.example.study.repository.cart_item.dto.DeleteCartItemDto
import org.example.study.repository.cart_item.dto.UpdateCartItemDto
import org.example.study.service.cart.request.CreateCartRequest
import org.example.study.service.cart.response.CreateCartResponse
import org.example.study.service.cart_item.request.CreateCartItemRequest
import org.example.study.service.cart_item.request.DeleteCartItemRequest
import org.example.study.service.cart_item.request.UpdateCartItemRequest
import org.example.study.service.cart_item.response.CreateCartItemResponse
import org.example.study.service.cart_item.response.DeleteCartItemResponse
import org.example.study.service.cart_item.response.UpdateCartItemResponse

class CartService(
    private var cartRepository: CartRepository
) {
    fun create(req: CreateCartRequest): CreateCartResponse {
        val dto = CreateCartDto(req.userId)
        val created = cartRepository.createCart(dto)
        return CreateCartResponse(created.cartId, created.cartItems, created.userId)
    }

    fun createCartItem(req: CreateCartItemRequest): CreateCartItemResponse {
        val dto = CreateCartItemDto(req.cartId, req.itemId, req.cnt)
        val vo = cartRepository.createCartItem(dto)
        return CreateCartItemResponse(vo.cartId, vo.cartItemId, vo.cnt)
    }

    fun deleteCartItem(req: DeleteCartItemRequest): DeleteCartItemResponse {
        val dto = DeleteCartItemDto(req.cartId, req.cartItemId)
        val vo = cartRepository.deleteCartItem(dto)
        return DeleteCartItemResponse(vo.cartId, vo.cartItems)
    }

    fun updateCartItem(req: UpdateCartItemRequest): UpdateCartItemResponse {
        val dto = UpdateCartItemDto(req.cartId, req.cartItemId, req.cnt)
        val vo = cartRepository.updateCartItem(dto)
        return UpdateCartItemResponse(vo.cartId, vo.cartItemId, vo.cnt)
    }
}
