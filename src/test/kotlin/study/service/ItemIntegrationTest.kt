package study.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.example.study.domain.id.Ids
import org.example.study.repository.ItemRepository
import org.example.study.repository.item.dto.GetItemDto

class ItemIntegrationTest: DescribeSpec({
    val itemRepository = ItemRepository()

    describe("상품 조회") {
        context("성공 케이스") {
            it("상품 ID 로 조회") {
                val nonNullableItemId = Ids.ItemId(1L)
                val dto = GetItemDto(nonNullableItemId)
                val dbItem = itemRepository.findById(dto)

                println("dbItem : $dbItem")

                with(dbItem) {
                    shouldNotBeNull()
                    itemId shouldBe nonNullableItemId
                }
            }
        }

        context("실패 케이스") {
            it("존재하지 않는 상품 ID") {
                val notFoundId = Ids.ItemId(20L)
                val dto = GetItemDto(notFoundId)
                shouldThrow<IllegalArgumentException> {
                    itemRepository.findById(dto)
                }
            }
        }
    }
})

