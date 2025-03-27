package study.usecase

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.example.study.domain.policy.CartPolicy
import org.example.study.exception.CartAlreadyExistException
import org.example.study.exception.TaskException
import org.example.study.repository.CartRepository
import org.example.study.usecase.CartUseCases
import org.example.study.usecase.cart.in_msg.CreateCartInMessage
import org.example.study.usecase.cart.in_msg.GetCartByUserInMessage
import org.example.study.usecase.cart.out_msg.CreateCartOutMessage
import org.example.study.usecase.cart.out_msg.GetCartByUserOutMessage
import study.generator.TestFactory

@DisplayName("장바구니 통합 테스트")
class CartIntegrationTests: DescribeSpec({
    lateinit var createCartUseCase: CartUseCases.CreateCartUseCase<CreateCartInMessage, CreateCartOutMessage>
    lateinit var getCartUseCase: CartUseCases.GetCartUseCase<GetCartByUserInMessage, GetCartByUserOutMessage>

    val createInMsg = CreateCartInMessage(TestFactory.testUser)

    beforeEach {
        val cartRepository = CartRepository()
        val cartPolicy = TestFactory.cartPolicy()
        createCartUseCase = TestFactory.createCartUseCase(cartRepository, cartPolicy)
        getCartUseCase = TestFactory.getCartUseCase(cartRepository)
    }

    describe("장바구니 생성") {
        context("성공 케이스") {
            it("생성 성공") {
                shouldNotThrow<TaskException> {
                    val outMsg = createCartUseCase.create(createInMsg)

                    with(outMsg) {
                        cart.userId shouldBe TestFactory.testUser
                    }
                }
            }
        }
        context("실패 케이스") {
            it("장바구니 생성 실패 - already exist") {
                createCartUseCase.create(createInMsg) // first create

                shouldThrow<CartAlreadyExistException> {
                    val secondCreateInMsg = CreateCartInMessage(TestFactory.testUser)
                    createCartUseCase.create(secondCreateInMsg)
                }
            }
        }
    }

    it("장바구니 사용자 ID로 조회") {
        val createdCartRes = createCartUseCase.create(createInMsg)
        val inMsg = GetCartByUserInMessage(TestFactory.testUser)

        shouldNotThrow<TaskException> {
            val response = getCartUseCase.get(inMsg)

            with(response.cart!!) {
                cartId shouldBe createdCartRes.cart.cartId
                userId shouldBe createdCartRes.cart.userId
            }
        }
    }
})
