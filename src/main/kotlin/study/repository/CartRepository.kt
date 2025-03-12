package org.example.study.repository

import org.example.study.domain.entity.Cart
import org.example.study.domain.entity.CartItem
import org.example.study.domain.id.Ids
import org.example.study.repository.dto.CreateCartDto
import org.example.study.repository.dto.CreateCartItemDto
import org.example.study.repository.dto.DeleteCartItemDto
import org.example.study.repository.dto.UpdateCartItemDto
import org.example.study.repository.vo.CreateCartItemVo
import org.example.study.repository.vo.CreateCartVo
import org.example.study.repository.vo.DeleteCartItemVo
import org.example.study.repository.vo.UpdateCartItemVo

class CartRepository(
    private var cartNum: Long = 1L,
    private var cartItemNum: Long = 1L,
    private val carts: MutableMap<Ids.CartId, MutableList<CartItem>> = mutableMapOf()
) {

    fun createCart(dto: CreateCartDto): CreateCartVo {
        val cartId = Ids.CartId(cartNum)
        val created = Cart(cartId, ArrayList(), dto.userId)

        carts[created.cartId] = created.cartItems
        cartNum = Ids.autoIncrement(cartNum)

        return CreateCartVo(created.cartId, carts.getOrDefault(created.cartId, ArrayList()), dto.userId)
    }

    fun createCartItem(dto: CreateCartItemDto): CreateCartItemVo {
        val cartItem = CartItem(Ids.CartItemId(cartItemNum), dto.cartId, dto.itemId, dto.cnt)

        val target = carts.getOrDefault(cartItem.cartId, mutableListOf())
        target.add(cartItem)

        val created = target.first { it.itemId == cartItem.itemId}
        cartItemNum = Ids.autoIncrement(cartItemNum)

        return CreateCartItemVo(created.cartId, created.cartItemId, created.cnt)
    }

    fun deleteCartItem(dto: DeleteCartItemDto): DeleteCartItemVo {
        carts.remove(dto.cartId)
        return DeleteCartItemVo(dto.cartId, carts.getOrDefault(dto.cartId, ArrayList()))
    }

    fun updateCartItem(dto: UpdateCartItemDto): UpdateCartItemVo {
        val target = carts.getOrDefault(dto.cartId, ArrayList()).first { it.cartItemId == dto.cartItemId }
        target.cnt = dto.cnt

        return UpdateCartItemVo(dto.cartId, dto.cartItemId, target.cnt)
    }
}
