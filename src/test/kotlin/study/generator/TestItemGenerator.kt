package study.generator

import org.example.study.domain.entity.Item
import org.example.study.domain.id.Ids
import kotlin.random.Random

object TestItemGenerator {
    fun generate(): Map<Ids.ItemId, Item> = (1L..10L).associate { num ->
        val itemId = Ids.ItemId(num)
        itemId to Item(itemId, name(num), price(), fee())
    }.toMap()

    private fun name(num: Long): String = "상품$num"
    private fun price(): Long = Random.nextLong(5000L, 100001L)
    private fun fee(): Long = Random.nextLong(0L, 50001L)
}
