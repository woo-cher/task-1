package study.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import study.ItemRepository
import study.generator.TestFactory
import study.generator.TestFactory.toItemId
import study.item.dto.GetItemDto
import study.type.exception.ItemNotFoundException

@DisplayName("상품 통합 테스트")
class ItemIntegrationTest: DescribeSpec({
    val itemRepository = ItemRepository()

    describe("상품 조회") {
        context("성공 케이스") {
            it("상품 ID 로 조회 가능") {
                val nonNullableItemId = TestFactory.START_ID.toItemId()
                val dto = GetItemDto(nonNullableItemId)
                val dbItem = itemRepository.findById(dto)

                with(dbItem) {
                    shouldNotBeNull()
                    itemId shouldBe nonNullableItemId
                }
            }
        }

        context("실패 케이스") {
            it("상품 조회 실패 - not found") {
                val notFoundId = TestFactory.invalidId().toItemId()
                val dto = GetItemDto(notFoundId)

                shouldThrow<ItemNotFoundException> {
                    itemRepository.findById(dto)
                }
            }
        }
    }
})

