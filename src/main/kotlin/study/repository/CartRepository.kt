package org.example.study.repository

import org.example.study.domain.entity.Cart
import org.example.study.domain.entity.CartItem
import org.example.study.domain.enums.ShippingStatus
import org.example.study.domain.id.Ids
import org.example.study.repository.cart.dto.CreateCartDto
import org.example.study.repository.cart_item.dto.CreateCartItemDto
import org.example.study.repository.cart_item.dto.DeleteCartItemDto
import org.example.study.repository.cart_item.dto.UpdateCartItemDto
import org.example.study.repository.cart_item.vo.CreateCartItemVo
import org.example.study.repository.cart.vo.CreateCartVo
import org.example.study.repository.cart_item.vo.DeleteCartItemVo
import org.example.study.repository.cart_item.vo.UpdateCartItemVo

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
        val cartItem = CartItem(Ids.CartItemId(cartItemNum), dto.cartId, dto.itemId, 1000, dto.cnt, ShippingStatus.NONE)

        val target = carts.getOrDefault(cartItem.cartId, mutableListOf())
        target.add(cartItem)

        val created = target.first { it.itemId == cartItem.itemId}
        cartItemNum = Ids.autoIncrement(cartItemNum)

        return CreateCartItemVo(created.cartId, created.cartItemId, created.cnt)
    }

    fun deleteCartItem(dto: DeleteCartItemDto): DeleteCartItemVo {
        carts[dto.cartId]?.removeIf { it.cartItemId == dto.cartItemId }
        return DeleteCartItemVo(dto.cartId, carts.getOrDefault(dto.cartId, ArrayList()))
    }

    fun updateCartItem(dto: UpdateCartItemDto): UpdateCartItemVo {
        val target = carts.getOrDefault(dto.cartId, ArrayList()).first { it.cartItemId == dto.cartItemId }
        target.cnt = dto.cnt

        return UpdateCartItemVo(dto.cartId, dto.cartItemId, target.cnt)
    }
}
