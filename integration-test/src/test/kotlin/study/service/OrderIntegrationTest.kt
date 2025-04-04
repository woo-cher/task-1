package study.service

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.inspectors.forAll
import io.kotest.inspectors.shouldForAll
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.shouldBe
import study.CartRepository
import study.OrderRepository
import study.cart.dto.CreateCartDto
import study.cart_item.dto.CreateCartItemDto
import study.generator.TestCartItemGenerator
import study.generator.TestFactory
import study.generator.TestItemGenerator
import study.handler.UseCaseHandlerProxy
import study.order.in_msg.CreateOrderInMessage
import study.order.out_msg.CreateOrderOutMessage
import study.persistence.Cart
import study.persistence.CartItem
import study.type.enums.OrderStatus
import study.type.exception.CartNotFoundException
import study.type.exception.TaskException

@DisplayName("주문 통합 테스트")
class OrderIntegrationTest: DescribeSpec({
    lateinit var createOrderUseCase: UseCaseHandlerProxy<CreateOrderInMessage, CreateOrderOutMessage>
    lateinit var createdCart: Cart
    lateinit var cartItems: List<CartItem>

    val items = TestItemGenerator.generate()

    beforeEach {
        val orderRepository = OrderRepository()
        val cartRepository = CartRepository()
        createOrderUseCase = TestFactory.createOrderProxy(orderRepository, cartRepository)

        createdCart = cartRepository.createCart(CreateCartDto(TestFactory.testUser)).cart
        cartItems = TestCartItemGenerator.generate(items, createdCart.cartId)
        cartItems.forAll {
            val dto = CreateCartItemDto(TestFactory.testUser, it.cartId, it.itemId, it.price, it.cnt)
            cartRepository.createCartItem(dto)
        }
    }

    describe("주문 생성") {
        context("성공 케이스") {
            it("주문 생성 성공") {
                val inMsg = CreateOrderInMessage(TestFactory.testUser, createdCart.cartId, cartItems)

                shouldNotThrow<TaskException> {
                    val outMsg = createOrderUseCase.execute(inMsg)

                    with(outMsg) {
                        cartItems.shouldNotBeEmpty()
                        userId shouldBe TestFactory.testUser
                        status shouldBe OrderStatus.PROCESSED
                    }
                    with(outMsg.cartItems) {
                        shouldForAll { it.cartId shouldBe createdCart.cartId }
                    }
                }
            }
        }
        context("실패 케이스") {
            it("주문 생성 실패 - 유저 ID 에 해당하는 장바구니가 없음") {
                val inMsg = CreateOrderInMessage(TestFactory.invalidTestUser, createdCart.cartId, cartItems)

                shouldThrow<CartNotFoundException> {
                    createOrderUseCase.execute(inMsg)
                }
            }
        }
    }
})
