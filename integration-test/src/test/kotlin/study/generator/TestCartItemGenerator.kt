package study.generator

import study.persistence.CartItem
import study.persistence.Item
import study.type.enums.ShippingStatus
import study.type.id.Ids
import kotlin.random.Random

object TestCartItemGenerator {
    fun generate(items: Map<Ids.ItemId, Item>, cartId: Ids.CartId): List<CartItem> {
        var cartItemId = 0L
        return items.values.map {
            val cartItem = CartItem(Ids.CartItemId(cartItemId), cartId, it.itemId, it.price, randCnt(), ShippingStatus.NONE)
            cartItemId = Ids.autoIncrement(cartItemId)
            cartItem
        }.toList()
    }

    private fun randCnt() = Random.nextInt(1, 10)
}
