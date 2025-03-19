package study.service

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.example.study.domain.id.Ids
import org.example.study.repository.CartRepository
import org.example.study.service.CartService
import org.example.study.service.cart_item.request.CreateCartItemRequest
import org.example.study.service.cart.request.CreateCartRequest
import org.example.study.service.cart_item.request.DeleteCartItemRequest
import org.example.study.service.cart_item.request.UpdateCartItemRequest

class CartIntegrationTest: FunSpec({
    lateinit var cartService: CartService
    val testUserId = Ids.UserId("testUser")
    val createCartRequest = CreateCartRequest(testUserId)

    beforeTest {
        cartService = CartService(CartRepository())
    }

    test("장바구니 생성") {
        val created = cartService.create(createCartRequest)
        println("created : $created")

        with(created) {
            cartItems.shouldNotBeEmpty()
            userId shouldBe testUserId
        }
    }

    test("장바구니 상품 추가") {
        val cart = cartService.create(createCartRequest)
        val request = CreateCartItemRequest(cart.cartId, Ids.ItemId(1L), 1)

        val response = cartService.createCartItem(request)

        println("created : $response")

        with(response) {
            shouldNotBeNull()
            cartId shouldBe request.cartId
            cnt shouldBe request.cnt
        }
    }

    test("장바구니 상품 제거") {
        val cart = cartService.create(createCartRequest)
        val createRequest = CreateCartItemRequest(cart.cartId, Ids.ItemId(1L), 1)
        val createVo = cartService.createCartItem(createRequest)
        val deleteRequest = DeleteCartItemRequest(createRequest.cartId, createVo.cartItemId)

        val deleteResponse = cartService.deleteCartItem(deleteRequest)

        deleteResponse.cartId shouldBe deleteRequest.cartId
        deleteResponse.cartItems.none { it.cartId == deleteRequest.cartId } shouldBe true
    }

    test("장바구니 상품 수량 변경") {
        val cart = cartService.create(createCartRequest)
        val createRequest = CreateCartItemRequest(cart.cartId, Ids.ItemId(1L), 1)
        val createVo = cartService.createCartItem(createRequest)

        val updateCnt = 2
        val updateRequest = UpdateCartItemRequest(createRequest.cartId, createVo.cartItemId, updateCnt)

        val updateResponse = cartService.updateCartItem(updateRequest)

        with(updateResponse) {
            cartId shouldBe updateRequest.cartId
            cartItemId shouldBe updateRequest.cartItemId
            cnt shouldBe updateCnt
        }
    }
})
