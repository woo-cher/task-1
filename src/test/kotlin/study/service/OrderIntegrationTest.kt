package study.service

import io.kotest.core.spec.style.FunSpec
import io.kotest.inspectors.shouldForAll
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.shouldBe
import org.example.study.domain.enums.OrderStatus
import org.example.study.domain.id.Ids
import org.example.study.repository.OrderRepository
import org.example.study.service.OrderService
import org.example.study.service.order.request.CreateOrderRequest
import study.generator.TestCartItemGenerator
import study.generator.TestItemGenerator

class OrderIntegrationTest: FunSpec({
    lateinit var orderService: OrderService
    val testUser: Ids.UserId = Ids.UserId("testUser")
    val testCart: Ids.CartId = Ids.CartId(1)

    beforeTest {
        orderService = OrderService(OrderRepository())
    }

    test("주문 생성") {
        val items = TestItemGenerator.generate()
        val cartItems = TestCartItemGenerator.generate(items, testCart)
        val request = CreateOrderRequest(testUser, cartItems)
        val response = orderService.create(request)

        println("created: $response")

        with(response) {
            response.cartItems.shouldNotBeEmpty()
            response.userId shouldBe testUser
            response.status shouldBe OrderStatus.PROCESSED
        }

        with(response.cartItems) {
            shouldForAll { it.cartId shouldBe testCart }
        }
    }
})
