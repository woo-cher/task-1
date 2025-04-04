package study

import study.persistence.Item
import study.type.id.Ids
import kotlin.random.Random

object Generator {
    private const val START_ID = 1L
    private const val TEST_SIZE = 10L

    fun generateItems(): Map<Ids.ItemId, Item> = (START_ID..TEST_SIZE).associate { num ->
        val itemId = Ids.ItemId(num)
        itemId to Item(itemId, name(num), price(), fee())
    }.toMap()

    fun startId(): Long = START_ID
    fun testSize(): Long = TEST_SIZE

    private fun name(num: Long): String = "상품$num"
    private fun price(): Long = Random.nextLong(5000L, 100001L)
    private fun fee(): Long = Random.nextLong(0L, 50001L)
}
