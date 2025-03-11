package study.service

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.example.study.domain.id.Ids
import org.example.study.repository.CartRepository
import org.example.study.service.CartService
import org.example.study.service.request.CreateCartItemRequest
import org.example.study.service.request.DeleteCartItemRequest

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

        println("created : $response")

        with(response) {
            shouldNotBeNull()
            cartId shouldBe request.cartId
            cnt shouldBe request.cnt
        }
    }

    test("장바구니 상품 제거") {
        val createRequest = CreateCartItemRequest(Ids.CartId(1L), Ids.ItemId(1L), 1)
        val createVo = cartService.addCartItem(createRequest)
        val deleteRequest = DeleteCartItemRequest(createRequest.cartId, createVo.cartItemId)
        val deleteResponse = cartService.deleteCartItem(deleteRequest)

        deleteResponse.cartId shouldBe deleteRequest.cartId
        deleteResponse.cartItems.none { it.cartId == deleteRequest.cartId } shouldBe true
    }
})
