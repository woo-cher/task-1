package study.service

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldNotContainAnyOf
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.example.study.domain.id.Ids
import org.example.study.domain.policy.CartPolicy
import org.example.study.exception.CartNotFoundException
import org.example.study.exception.ItemNotFoundException
import org.example.study.exception.TaskException
import org.example.study.repository.CartRepository
import org.example.study.repository.ItemRepository
import org.example.study.service.CartService
import org.example.study.service.cart.request.CreateCartRequest
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

    describe("장바구니 상품 제거") {
        context("성공 케이스") {
            it("장바구니 상품 제거 성공") {
                val createdCartRes = cartService.create(createCartRequest)
                val createRequest = CreateCartItemRequest(testUserId, createdCartRes.cart.cartId, Ids.ItemId(1L), 1)
                val createVo = cartService.createCartItem(createRequest)
                val deleteItemIds = listOf(createVo.cartItemId)
                val deleteRequest = DeleteCartItemsRequest(testUserId, createRequest.cartId, deleteItemIds)

                shouldNotThrow<TaskException> {
                    val deleteResponse = cartService.deleteCartItems(deleteRequest)

                    deleteResponse.cartId shouldBe deleteRequest.cartId
                    deleteResponse.cartItems shouldNotContainAnyOf deleteItemIds
                }
            }
        }
        context("실패 케이스") {
            it("제거 실패 - 존재하지 않는 사용자 장바구니") {
                val notFoundCartId = Ids.CartId(1L)
                val createRequest = CreateCartItemRequest(testUserId, notFoundCartId, Ids.ItemId(1L), 1)

                shouldThrow<CartNotFoundException> {
                    cartService.createCartItem(createRequest)
                }
            }
        }
    }

    describe("장바구니 상품 수량 변경") {
        context("성공 케이스") {
            it("수량 변경 성공") {
                val createdCartRes = cartService.create(createCartRequest)
                val createRequest = CreateCartItemRequest(testUserId, createdCartRes.cart.cartId, Ids.ItemId(1L), 1)
                val createVo = cartService.createCartItem(createRequest)

                val updateCnt = 2
                val updateRequest = UpdateCartItemRequest(testUserId, createRequest.cartId, createVo.cartItemId, updateCnt)

                shouldNotThrow<TaskException> {
                    val updateResponse = cartService.updateCartItem(updateRequest)

                    with(updateResponse) {
                        cartId shouldBe updateRequest.cartId
                        cartItemId shouldBe updateRequest.cartItemId
                        cnt shouldBe updateCnt
                    }
                }
            }
            it("수량 변경 실패 - 존재하지 않는 사용자 장바구니") {
                val createdCartRes = cartService.create(createCartRequest)
                val createRequest = CreateCartItemRequest(testUserId, createdCartRes.cart.cartId, Ids.ItemId(1L), 1)
                val createVo = cartService.createCartItem(createRequest)

                val invalidUser = Ids.UserId("invalid")
                val updateCnt = 2
                val updateRequest = UpdateCartItemRequest(invalidUser, createRequest.cartId, createVo.cartItemId, updateCnt)

                shouldThrow<CartNotFoundException> {
                    cartService.updateCartItem(updateRequest)
                }
            }
        }
    }
})
