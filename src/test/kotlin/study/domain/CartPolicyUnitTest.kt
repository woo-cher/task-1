package study.domain

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import org.example.study.domain.entity.Cart
import org.example.study.domain.id.Ids
import study.generator.TestFactory
import study.generator.TestFactory.toCartId

@DisplayName("장바구니 정책 도메인 테스트")
class CartPolicyUnitTest: DescribeSpec({
    val cartPolicy = TestFactory.cartPolicy()
    val testCartId: Ids.CartId = TestFactory.START_ID.toCartId()

    describe("장바구니 생성 정책") {
        it("생성 가능") {
            val cartSupplier = { null }

            shouldNotThrow<RuntimeException> {
                cartPolicy.validateExistsOrThrow(cartSupplier) { _ -> throw RuntimeException() }
            }
        }
        it("생성 실패 - already exist") {
            val cartSupplier = { Cart(testCartId, mutableListOf(), TestFactory.testUser) }

            shouldThrow<RuntimeException> {
                cartPolicy.validateExistsOrThrow(cartSupplier) { _ -> throw RuntimeException() }
            }
        }
    }
})
