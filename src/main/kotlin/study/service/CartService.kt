package org.example.study.service

import org.example.study.repository.CartRepository
import org.example.study.repository.dto.CreateCartDto
import org.example.study.repository.dto.CreateCartItemDto
import org.example.study.repository.dto.DeleteCartItemDto
import org.example.study.repository.dto.UpdateCartItemDto
import org.example.study.repository.vo.CreateCartVo
import org.example.study.service.reponse.CreateCartItemResponse
import org.example.study.service.reponse.DeleteCartItemResponse
import org.example.study.service.reponse.UpdateCartItemResponse
import org.example.study.service.request.CreateCartItemRequest
import org.example.study.service.request.CreateCartRequest
import org.example.study.service.request.DeleteCartItemRequest
import org.example.study.service.request.UpdateCartItemRequest

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
