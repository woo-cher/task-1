package org.example.study.repository

import org.example.study.domain.entity.Cart
import org.example.study.domain.entity.CartItem
import org.example.study.domain.id.Ids
import org.example.study.repository.dto.CreateCartItemDto
import org.example.study.repository.dto.DeleteCartItemDto
import org.example.study.repository.vo.CreateCartItemVo
import org.example.study.repository.vo.CreateCartVo
import org.example.study.repository.vo.DeleteCartItemVo

class CartRepository(
    private var cartNum: Long = 1L,
    private var cartItemNum: Long = 1L,
    private val carts: MutableMap<Ids.CartId, List<CartItem>> = mutableMapOf()
) {

    fun createCart(): CreateCartVo {
        val cartId = Ids.CartId(cartNum)
        val created = Cart(cartId, ArrayList())
        cartNum = Ids.autoIncrement(cartNum)
        return CreateCartVo(created.cartId, carts.getOrDefault(created.cartId, ArrayList()))
    }

    fun createCartItem(dto: CreateCartItemDto): CreateCartItemVo {
        val created = CreateCartItemVo(dto.cartId, Ids.CartItemId(cartItemNum), dto.cnt)
        cartItemNum = Ids.autoIncrement(cartItemNum)
        return created
    }

    fun deleteCartItem(dto: DeleteCartItemDto): DeleteCartItemVo {
        this.carts.remove(dto.cartId)
        return DeleteCartItemVo(dto.cartId, carts.getOrDefault(dto.cartId, ArrayList()))
    }
}
