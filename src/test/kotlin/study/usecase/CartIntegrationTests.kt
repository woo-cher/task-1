package study.usecase

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.example.study.exception.CartAlreadyExistException
import org.example.study.exception.TaskException
import org.example.study.repository.CartRepository
import org.example.study.usecase.UseCaseHandlerProxy
import org.example.study.usecase.cart.in_msg.CreateCartInMessage
import org.example.study.usecase.cart.in_msg.GetCartByUserInMessage
import org.example.study.usecase.cart.out_msg.CreateCartOutMessage
import org.example.study.usecase.cart.out_msg.GetCartByUserOutMessage
import study.generator.TestFactory

@DisplayName("장바구니 통합 테스트")
class CartIntegrationTests: DescribeSpec({
    lateinit var createCartUseCase: UseCaseHandlerProxy<CreateCartInMessage, CreateCartOutMessage>
    lateinit var getCartUseCase: UseCaseHandlerProxy<GetCartByUserInMessage, GetCartByUserOutMessage>

    val createInMsg = CreateCartInMessage(TestFactory.testUser)

    beforeEach {
        val cartRepository = CartRepository()
        val cartPolicy = TestFactory.cartPolicy()
        createCartUseCase = TestFactory.createCartProxy(cartRepository, cartPolicy)
        getCartUseCase = TestFactory.getCartProxy(cartRepository)
    }

    describe("장바구니 생성") {
        context("성공 케이스") {
            it("생성 성공") {
                shouldNotThrow<TaskException> {
                    val outMsg = createCartUseCase.execute(createInMsg)

                    with(outMsg) {
                        cart.userId shouldBe TestFactory.testUser
                    }
                }
            }
        }
        context("실패 케이스") {
            it("장바구니 생성 실패 - already exist") {
                createCartUseCase.execute(createInMsg) // first create

                shouldThrow<CartAlreadyExistException> {
                    val secondCreateInMsg = CreateCartInMessage(TestFactory.testUser)
                    createCartUseCase.execute(secondCreateInMsg)
                }
            }
        }
    }

    it("장바구니 사용자 ID로 조회") {
        val createdCartRes = createCartUseCase.execute(createInMsg)
        val inMsg = GetCartByUserInMessage(TestFactory.testUser)

        shouldNotThrow<TaskException> {
            val response = getCartUseCase.execute(inMsg)

            with(response.cart!!) {
                cartId shouldBe createdCartRes.cart.cartId
                userId shouldBe createdCartRes.cart.userId
            }
        }
    }
})
