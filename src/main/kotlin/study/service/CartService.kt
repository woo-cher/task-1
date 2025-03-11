package org.example.study.service

import org.example.study.domain.entity.Cart
import org.example.study.repository.CartRepository
import org.example.study.repository.dto.CreateCartItemDto
import org.example.study.repository.vo.CreateCartVo
import org.example.study.service.reponse.CreateCartItemResponse
import org.example.study.service.request.CreateCartItemRequest

class CartService(
    private var cartRepository: CartRepository
) {
    // 장바구니 생성
    fun addCart(): CreateCartVo {
        val created = cartRepository.createCart()
        return CreateCartVo(created.id, created.cartItems)
    }

    // 장바구니 상품 추가
    fun addCartItem(req : CreateCartItemRequest) : CreateCartItemResponse {
        val dto = CreateCartItemDto(req.cartId, req.itemId, req.cnt)
        val vo = cartRepository.createCartItem(dto)
        return CreateCartItemResponse(vo.cartId, vo.itemId, vo.cnt)
    }

    // 장바구니 상품 제거
    fun deleteItem() {

    }

    // 장바구니 상품 수정 (수량, 옵션 등..)
    fun updateItem() {

    }
}
