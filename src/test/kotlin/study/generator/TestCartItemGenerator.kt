package study.generator

import org.example.study.domain.entity.CartItem
import org.example.study.domain.entity.Item
import org.example.study.domain.enums.ShippingStatus
import org.example.study.domain.id.Ids
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
