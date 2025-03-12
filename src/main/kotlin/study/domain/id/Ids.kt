package org.example.study.domain.id

object Ids {
    @JvmInline
    value class ItemId(val id: Long)
    @JvmInline
    value class CartId(val id: Long)
    @JvmInline
    value class CartItemId(val id: Long)
    @JvmInline
    value class OrderId(val id: Long)
    @JvmInline
    value class UserId(val id: String)

    fun autoIncrement(num: Long): Long {
        return num + 1
    }
}
