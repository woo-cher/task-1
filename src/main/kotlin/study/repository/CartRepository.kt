package org.example.study.repository

import org.example.study.domain.entity.Cart
import org.example.study.domain.entity.CartItem
import org.example.study.domain.id.Ids
import org.example.study.repository.dto.CreateCartItemDto
import org.example.study.repository.vo.CreateCartItemVo

class CartRepository(
    private var cartNum: Long = 1L,
    private val carts: MutableMap<Ids.CartId, List<CartItem>> = mutableMapOf()
) {

    fun createCart() : Cart {
        val cartId = Ids.CartId(cartNum)
        val created = Cart(cartId, ArrayList())
        cartNum = Ids.autoIncrement(cartNum)
        return created
    }

    fun createCartItem(dto: CreateCartItemDto) : CreateCartItemVo {
        return CreateCartItemVo(dto.cartId, dto.itemId, dto.cnt)
    }
}
