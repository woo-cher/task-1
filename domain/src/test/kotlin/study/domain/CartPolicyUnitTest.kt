package study.domain

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import study.policy.CartPolicy
import study.type.data.CartData
import study.type.id.Ids

@DisplayName("장바구니 정책 도메인 테스트")
class CartPolicyUnitTest: DescribeSpec({
    val cartPolicy = CartPolicy()
    val testCartId: Ids.CartId = Ids.CartId(1L)
    val testUser: Ids.UserId = Ids.UserId("testUser")

    describe("장바구니 생성 정책") {
        it("생성 가능") {
            val cartSupplier = { null }

            shouldNotThrow<RuntimeException> {
                cartPolicy.validateExistsOrThrow(cartSupplier) { _ -> throw RuntimeException() }
            }
        }
        it("생성 실패 - already exist") {
            val cartSupplier = { CartData(testCartId, mutableListOf(), testUser) }

            shouldThrow<RuntimeException> {
                cartPolicy.validateExistsOrThrow(cartSupplier) { _ -> throw RuntimeException() }
            }
        }
    }
})
