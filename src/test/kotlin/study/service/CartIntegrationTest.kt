package study.service

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.example.study.domain.id.Ids
import org.example.study.repository.CartRepository
import org.example.study.service.CartService
import org.example.study.service.request.CreateCartItemRequest

class CartIntegrationTest : FunSpec({
    lateinit var cartService: CartService

    beforeTest {
        cartService = CartService(CartRepository())
    }

    test("장바구니 생성") {
        val created = cartService.addCart()
        println("created : $created")
        created.shouldNotBeNull()
    }

    test("장바구니 상품 추가") {
        val request = CreateCartItemRequest(Ids.CartId(1L), Ids.ItemId(1L), 1)
        val response = cartService.addCartItem(request)

        println("added : $response")

        with(response) {
            shouldNotBeNull()
            cartId shouldBe request.cartId
            itemId shouldBe request.itemId
            cnt shouldBe request.cnt
        }
    }
})
