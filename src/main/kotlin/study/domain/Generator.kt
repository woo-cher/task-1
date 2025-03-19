package org.example.study.domain

import org.example.study.domain.entity.Item
import org.example.study.domain.id.Ids
import kotlin.random.Random

object Generator {
    fun generateItems(): MutableMap<Ids.ItemId, Item> = (1L..10L).associate { num ->
        val itemId = Ids.ItemId(num)
        itemId to Item(itemId, name(num), price(), fee())
    }.toMutableMap()

    private fun name(num: Long): String = "상품$num"
    private fun price(): Long = Random.nextLong(5000L, 100001L)
    private fun fee(): Long = Random.nextLong(0L, 50001L)
}
