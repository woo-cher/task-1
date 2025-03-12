package org.example.study.service

import org.example.study.repository.CartRepository
import org.example.study.repository.cart.dto.CreateCartDto
import org.example.study.repository.cart_item.dto.CreateCartItemDto
import org.example.study.repository.cart_item.dto.DeleteCartItemDto
import org.example.study.repository.cart_item.dto.UpdateCartItemDto
import org.example.study.repository.cart.vo.CreateCartVo
import org.example.study.service.cart_item.response.CreateCartItemResponse
import org.example.study.service.cart_item.response.DeleteCartItemResponse
import org.example.study.service.cart_item.response.UpdateCartItemResponse
import org.example.study.service.cart_item.request.CreateCartItemRequest
import org.example.study.service.cart.request.CreateCartRequest
import org.example.study.service.cart_item.request.DeleteCartItemRequest
import org.example.study.service.cart_item.request.UpdateCartItemRequest

class CartService(
    private var cartRepository: CartRepository
) {
    // 장바구니 생성
    fun create(req: CreateCartRequest): CreateCartVo {
        val dto = CreateCartDto(req.userId)
        val created = cartRepository.createCart(dto)
        return CreateCartVo(created.cartId, created.cartItems, created.userId)
    }

    // 장바구니 상품 추가
    fun createCartItem(req: CreateCartItemRequest): CreateCartItemResponse {
        val dto = CreateCartItemDto(req.cartId, req.itemId, req.cnt)
        val vo = cartRepository.createCartItem(dto)
        return CreateCartItemResponse(vo.cartId, vo.cartItemId, vo.cnt)
    }

    // 장바구니 상품 제거
    fun deleteCartItem(req: DeleteCartItemRequest): DeleteCartItemResponse {
        val dto = DeleteCartItemDto(req.cartId, req.cartItemId)
        val vo = cartRepository.deleteCartItem(dto)
        return DeleteCartItemResponse(vo.cartId, vo.cartItems)
    }

    // 장바구니 상품 수령 변경
    fun updateCartItem(req: UpdateCartItemRequest): UpdateCartItemResponse {
        val dto = UpdateCartItemDto(req.cartId, req.cartItemId, req.cnt)
        val vo = cartRepository.updateCartItem(dto)
        return UpdateCartItemResponse(vo.cartId, vo.cartItemId, vo.cnt)
    }
}
