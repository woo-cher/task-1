package study.service

import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.FunSpec
import io.kotest.inspectors.forAll
import io.kotest.inspectors.shouldForAll
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.shouldBe
import org.example.study.domain.enums.OrderStatus
import org.example.study.domain.id.Ids
import org.example.study.repository.CartRepository
import org.example.study.repository.OrderRepository
import org.example.study.repository.cart.dto.CreateCartDto
import org.example.study.repository.cart_item.dto.CreateCartItemDto
import org.example.study.service.OrderService
import org.example.study.service.order.request.CreateOrderRequest
import study.generator.TestCartItemGenerator
import study.generator.TestItemGenerator

@DisplayName("주문 통합 테스트")
class OrderIntegrationTest: FunSpec({
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

    test("주문 생성") {
        val request = CreateOrderRequest(testUser, testCart, cartItems)
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
})
