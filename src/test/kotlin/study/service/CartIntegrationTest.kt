package study.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldNotContainAnyOf
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.example.study.domain.id.Ids
import org.example.study.domain.policy.CartPolicy
import org.example.study.exception.CartAlreadyExistException
import org.example.study.repository.CartRepository
import org.example.study.repository.ItemRepository
import org.example.study.service.CartService
import org.example.study.service.cart.request.CreateCartRequest
import org.example.study.service.cart.request.GetCartByUserRequest
import org.example.study.service.cart_item.request.CreateCartItemRequest
import org.example.study.service.cart_item.request.DeleteCartItemsRequest
import org.example.study.service.cart_item.request.UpdateCartItemRequest

@DisplayName("장바구니 통합 테스트")
class CartIntegrationTest: DescribeSpec({
    lateinit var cartService: CartService
    val testUserId = Ids.UserId("testUser")
    val createCartRequest = CreateCartRequest(testUserId)

    beforeTest {
        cartService = CartService(CartRepository(), ItemRepository(), CartPolicy())
    }

    describe("장바구니 생성") {
        context("성공 케이스") {
            it("장바구니 정상 생성") {
                val createdCartRes = cartService.create(createCartRequest)
                println("created : $createdCartRes")

                with(createdCartRes) {
                    cart.userId shouldBe testUserId
                }
            }
        }
        context("실패 케이스") {
            it("장바구니 생성 실패 - already exist") {
                cartService.create(createCartRequest) // first create
                val secondCreateCartRequest = CreateCartRequest(testUserId)

                shouldThrow<CartAlreadyExistException> {
                    cartService.create(secondCreateCartRequest)
                }
            }
        }

    }

    it("장바구니 사용자 ID로 조회") {
        val createdCartRes = cartService.create(createCartRequest)
        val request = GetCartByUserRequest(testUserId)

        val response = cartService.getCartByUser(request)

        with(response.cart!!) {
            cartId shouldBe createdCartRes.cart.cartId
            userId shouldBe createdCartRes.cart.userId
        }
    }

    it("장바구니 상품 추가") {
        val createdCartRes = cartService.create(createCartRequest)
        val request = CreateCartItemRequest(testUserId, createdCartRes.cart.cartId, Ids.ItemId(1L), 1)

        val response = cartService.createCartItem(request)

        println("created : $response")

        with(response) {
            shouldNotBeNull()
            cartId shouldBe request.cartId
            cnt shouldBe request.cnt
        }
    }

    it("장바구니 상품 제거") {
        val createdCartRes = cartService.create(createCartRequest)
        val createRequest = CreateCartItemRequest(testUserId, createdCartRes.cart.cartId, Ids.ItemId(1L), 1)
        val createVo = cartService.createCartItem(createRequest)
        val deleteItemIds = listOf(createVo.cartItemId)
        val deleteRequest = DeleteCartItemsRequest(testUserId, createRequest.cartId, deleteItemIds)
        val deleteResponse = cartService.deleteCartItems(deleteRequest)

        deleteResponse.cartId shouldBe deleteRequest.cartId
        deleteResponse.cartItems shouldNotContainAnyOf deleteItemIds
    }

    it("장바구니 상품 수량 변경") {
        val createdCartRes = cartService.create(createCartRequest)
        val createRequest = CreateCartItemRequest(testUserId, createdCartRes.cart.cartId, Ids.ItemId(1L), 1)
        val createVo = cartService.createCartItem(createRequest)

        val updateCnt = 2
        val updateRequest = UpdateCartItemRequest(testUserId, createRequest.cartId, createVo.cartItemId, updateCnt)

        val updateResponse = cartService.updateCartItem(updateRequest)

        with(updateResponse) {
            cartId shouldBe updateRequest.cartId
            cartItemId shouldBe updateRequest.cartItemId
            cnt shouldBe updateCnt
        }
    }
})
