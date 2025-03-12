package study.service

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.maps.shouldNotBeEmpty
import org.example.study.repository.ItemRepository

class ItemIntegrationTest: FunSpec({

    test("더미 아이템") {
        val items = ItemRepository().findAll()
        println("items: $items")
        items.shouldNotBeEmpty()
    }
})
