package org.example.study.repository

import org.example.study.domain.entity.Cart
import org.example.study.domain.entity.CartItem
import org.example.study.domain.enums.ShippingStatus
import org.example.study.domain.id.Ids
import org.example.study.repository.cart.dto.CreateCartDto
import org.example.study.repository.cart.vo.CreateCartVo
import org.example.study.repository.cart_item.dto.CreateCartItemDto
import org.example.study.repository.cart_item.dto.DeleteCartItemsDto
import org.example.study.repository.cart_item.dto.UpdateCartItemDto
import org.example.study.repository.cart_item.vo.CreateCartItemVo
import org.example.study.repository.cart_item.vo.DeleteCartItemsVo
import org.example.study.repository.cart_item.vo.UpdateCartItemVo

class CartRepository(
    private var cartNum: Long = 1L,
    private var cartItemNum: Long = 1L,
    private val carts: MutableMap<Ids.CartId, MutableList<CartItem>> = mutableMapOf(),
    private val cartszz: MutableMap<Ids.UserId, Cart> = mutableMapOf()
) {

    fun createCart(dto: CreateCartDto): CreateCartVo {
        val cartId = Ids.CartId(cartNum)
        val cart = Cart(cartId, ArrayList(), dto.userId)

        cartszz.put(dto.userId, cart)
        cartNum = Ids.autoIncrement(cartNum)

        return CreateCartVo(cart)
    }

    fun createCartItem(dto: CreateCartItemDto): CreateCartItemVo {
        val cartItem = CartItem(Ids.CartItemId(cartItemNum), dto.cartId, dto.itemId, dto.price, dto.cnt, ShippingStatus.NONE)

        val target = cartszz.get(dto.userId) ?: throw RuntimeException("not exist cart") // temp
        target.cartItems.add(cartItem)

        val created = target.cartItems.first { it.itemId == cartItem.itemId }
        cartItemNum = Ids.autoIncrement(cartItemNum)

        return CreateCartItemVo(created.cartId, created.cartItemId, created.cnt)
    }

    fun deleteCartItems(dto: DeleteCartItemsDto): DeleteCartItemsVo {
        val cart = cartszz[dto.userId] ?: throw RuntimeException("not exist cart") // temp
        cart.cartItems.removeAll { it.cartItemId in dto.cartItemIds }
        return DeleteCartItemsVo(dto.cartId, cart.cartItems)
    }

    fun updateCartItem(dto: UpdateCartItemDto): UpdateCartItemVo {
        val cart = cartszz.get(dto.userId) ?: throw RuntimeException("not exist cart") // temp
        val target = cart.cartItems.first { it.cartItemId == dto.cartItemId }
        target.cnt = dto.cnt

        return UpdateCartItemVo(dto.cartId, dto.cartItemId, target.cnt)
    }
}
