package study.service

import io.kotest.core.spec.style.FunSpec
import io.kotest.inspectors.shouldForAll
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.shouldBe
import org.example.study.domain.Generator
import org.example.study.domain.entity.CartItem
import org.example.study.domain.enums.OrderStatus
import org.example.study.domain.enums.ShippingStatus
import org.example.study.domain.id.Ids
import org.example.study.repository.OrderRepository
import org.example.study.service.OrderService
import org.example.study.service.order.request.CreateOrderRequest

class OrderIntegrationTest: FunSpec({
    lateinit var orderService: OrderService
    val testUser: Ids.UserId = Ids.UserId("testUser")
    val testCart: Ids.CartId = Ids.CartId(1)

    beforeTest {
        orderService = OrderService(OrderRepository())
    }

    test("주문 생성") {
        val items = Generator.generateItems()
        val cartItems = items.values.map {
            val cartItemId = 0L
            CartItem(Ids.CartItemId(cartItemId), testCart, it.itemId, it.price, 1, ShippingStatus.NONE)
        }
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
