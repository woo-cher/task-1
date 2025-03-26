package study.usecase

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.example.study.domain.id.Ids
import org.example.study.domain.policy.CartPolicy
import org.example.study.exception.CartAlreadyExistException
import org.example.study.exception.TaskException
import org.example.study.repository.CartRepository
import org.example.study.usecase.CartUseCases
import org.example.study.usecase.cart.CreateCartUseCase
import org.example.study.usecase.cart.in_msg.CreateCartInMessage
import org.example.study.usecase.cart.out_msg.CreateCartOutMessage

@DisplayName("장바구니 통합 테스트")
class CartIntegrationTests: DescribeSpec({
    lateinit var createCartUseCase: CartUseCases.CreateCartUseCase<CreateCartInMessage, CreateCartOutMessage>

    val testUserId = Ids.UserId("testUser")
    val createCartInMsg = CreateCartInMessage(testUserId)

    beforeTest {
        createCartUseCase = CreateCartUseCase(CartRepository(), CartPolicy())
    }

    describe("장바구니 생성") {
        context("성공 케이스") {
            it("생성 성공") {
                shouldNotThrow<TaskException> {
                    val outMsg = createCartUseCase.create(createCartInMsg)

                    with(outMsg) {
                        cart.userId shouldBe testUserId
                    }
                }
            }
        }
        context("실패 케이스") {
            it("장바구니 생성 실패 - already exist") {
                createCartUseCase.create(createCartInMsg) // first create

                shouldThrow<CartAlreadyExistException> {
                    val secondCreateInMsg = CreateCartInMessage(testUserId)
                    createCartUseCase.create(secondCreateInMsg)
                }
            }
        }
    }
})
