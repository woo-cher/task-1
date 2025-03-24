package study.service

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.inspectors.forAll
import io.kotest.inspectors.shouldForAll
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.shouldBe
import org.example.study.domain.enums.OrderStatus
import org.example.study.domain.id.Ids
import org.example.study.exception.CartNotFoundException
import org.example.study.exception.TaskException
import org.example.study.repository.CartRepository
import org.example.study.repository.OrderRepository
import org.example.study.repository.cart.dto.CreateCartDto
import org.example.study.repository.cart_item.dto.CreateCartItemDto
import org.example.study.service.OrderService
import org.example.study.service.order.request.CreateOrderRequest
import study.generator.TestCartItemGenerator
import study.generator.TestItemGenerator

@DisplayName("주문 통합 테스트")
class OrderIntegrationTest: DescribeSpec({
    lateinit var orderService: OrderService
    val cartRepository = CartRepository()

    val testUser: Ids.UserId = Ids.UserId("testUser")
    val testCart: Ids.CartId = Ids.CartId(1)

    val items = TestItemGenerator.generate()
    val cartItems = TestCartItemGenerator.generate(items, testCart)

    beforeTest {
        cartRepository.createCart(CreateCartDto(testUser))
        cartItems.forAll {
            val dto = CreateCartItemDto(testUser, it.cartId, it.itemId, it.price, it.cnt)
            cartRepository.createCartItem(dto)
        }

        orderService = OrderService(OrderRepository(), cartRepository)
    }

    describe("주문 생성") {
        context("성공 케이스") {
            it("주문 생성 성공") {
                val request = CreateOrderRequest(testUser, testCart, cartItems)

                shouldNotThrow<TaskException> {
                    val response = orderService.create(request)
                    println("created: $response")

                    with(response) {
                        cartItems.shouldNotBeEmpty()
                        userId shouldBe testUser
                        status shouldBe OrderStatus.PROCESSED
                    }
                    with(response.cartItems) {
                        shouldForAll { it.cartId shouldBe testCart }
                    }
                }
            }
        }
        context("실패 케이스") {
            it("주문 생성 실패 - 유저 ID 에 해당하는 장바구니가 없음") {
                val invalidUser = Ids.UserId("invalidUser")
                val request = CreateOrderRequest(invalidUser, testCart, cartItems)

                shouldThrow<CartNotFoundException> {
                    orderService.create(request)
                }
            }
        }
    }
})
