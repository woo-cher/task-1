package org.example.study.repository

import org.example.study.domain.entity.Cart
import org.example.study.domain.entity.CartItem
import org.example.study.domain.enums.ShippingStatus
import org.example.study.domain.id.Ids
import org.example.study.exception.CartNotFoundException
import org.example.study.exception.errors.TaskErrors
import org.example.study.repository.cart.dto.CreateCartDto
import org.example.study.repository.cart.dto.GetCartByUserDto
import org.example.study.repository.cart.vo.CreateCartVo
import org.example.study.repository.cart.vo.GetCartByUserVo
import org.example.study.repository.cart_item.dto.CreateCartItemDto
import org.example.study.repository.cart_item.dto.DeleteCartItemsDto
import org.example.study.repository.cart_item.dto.UpdateCartItemDto
import org.example.study.repository.cart_item.vo.CreateCartItemVo
import org.example.study.repository.cart_item.vo.DeleteCartItemsVo
import org.example.study.repository.cart_item.vo.UpdateCartItemVo
import org.example.study.service.generator.Generator

class CartRepository(
    private var cartNum: Long = Generator.startId(),
    private var cartItemNum: Long = Generator.startId(),
    private val carts: MutableMap<Ids.UserId, Cart> = mutableMapOf(),
    private val error: TaskErrors = TaskErrors.CART_NOT_FOUND
) {
    fun findCartByUser(dto: GetCartByUserDto): GetCartByUserVo {
        val target = carts.get(dto.userId)
        return GetCartByUserVo(target)
    }

    fun createCart(dto: CreateCartDto): CreateCartVo {
        val cartId = Ids.CartId(cartNum)
        val cart = Cart(cartId, ArrayList(), dto.userId)

        carts.put(dto.userId, cart)
        cartNum = Ids.autoIncrement(cartNum)

        return CreateCartVo(cart)
    }

    fun createCartItem(dto: CreateCartItemDto): CreateCartItemVo {
        val cartItem = CartItem(Ids.CartItemId(cartItemNum), dto.cartId, dto.itemId, dto.price, dto.cnt, ShippingStatus.NONE)

        val target = carts.get(dto.userId) ?: throw cartNotFoundException(dto.userId)
        target.cartItems.add(cartItem)

        val created = target.cartItems.first { it.itemId == cartItem.itemId }
        cartItemNum = Ids.autoIncrement(cartItemNum)

        return CreateCartItemVo(created.cartId, created.cartItemId, created.cnt)
    }

    fun deleteCartItems(dto: DeleteCartItemsDto): DeleteCartItemsVo {
        val cart = carts[dto.userId] ?: throw cartNotFoundException(dto.userId)
        cart.cartItems.removeAll { it.cartItemId in dto.cartItemIds }
        return DeleteCartItemsVo(dto.cartId, cart.cartItems)
    }

    fun updateCartItem(dto: UpdateCartItemDto): UpdateCartItemVo {
        val cart = carts.get(dto.userId) ?: throw cartNotFoundException(dto.userId)
        val target = cart.cartItems.first { it.cartItemId == dto.cartItemId }
        target.cnt = dto.cnt

        return UpdateCartItemVo(dto.cartId, dto.cartItemId, target.cnt)
    }

    private fun cartNotFoundException(userId: Ids.UserId) = CartNotFoundException(error.code, error.messageWith(userId.id))
}
