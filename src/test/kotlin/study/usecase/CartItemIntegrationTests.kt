package study.usecase

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldNotContainAnyOf
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.example.study.domain.entity.Cart
import org.example.study.domain.id.Ids
import org.example.study.domain.policy.CartPolicy
import org.example.study.exception.CartNotFoundException
import org.example.study.exception.ItemNotFoundException
import org.example.study.exception.TaskException
import org.example.study.repository.CartRepository
import org.example.study.repository.ItemRepository
import org.example.study.usecase.CartItemUseCases
import org.example.study.usecase.CartUseCases
import org.example.study.usecase.cart.CreateCartUseCase
import org.example.study.usecase.cart.in_msg.CreateCartInMessage
import org.example.study.usecase.cart.out_msg.CreateCartOutMessage
import org.example.study.usecase.cart_item.CreateCartItemUseCase
import org.example.study.usecase.cart_item.DeleteCartItemsUseCase
import org.example.study.usecase.cart_item.in_msg.CreateCartItemInMessage
import org.example.study.usecase.cart_item.in_msg.DeleteCartItemsInMessage
import org.example.study.usecase.cart_item.out_msg.CreateCartItemOutMessage
import org.example.study.usecase.cart_item.out_msg.DeleteCartItemsOutMessage

@DisplayName("장바구니 상품 통합 테스트")
class CartItemIntegrationTests: DescribeSpec({
    lateinit var createdCart: Cart

    lateinit var createCartUseCase: CartUseCases.CreateCartUseCase<CreateCartInMessage, CreateCartOutMessage>
    lateinit var createCartItemUseCase: CartItemUseCases.CreateCartItemUseCase<CreateCartItemInMessage, CreateCartItemOutMessage>
    lateinit var deleteCartItemUseCase: CartItemUseCases.DeleteCartItemsUseCase<DeleteCartItemsInMessage, DeleteCartItemsOutMessage>

    val testUserId = Ids.UserId("testUser")

    beforeEach {
        val cartRepository = CartRepository()
        val cartPolicy = CartPolicy()

        createCartUseCase = CreateCartUseCase(cartRepository, cartPolicy)
        createCartItemUseCase = CreateCartItemUseCase(cartRepository, ItemRepository())
        deleteCartItemUseCase = DeleteCartItemsUseCase(cartRepository)

        val createInMsg = CreateCartInMessage(testUserId)
        createdCart = createCartUseCase.create(createInMsg).cart
    }

    describe("장바구니 상품 추가") {
        context("성공 케이스") {
            it("장바구니 상품 추가 성공") {
                val inMsg = CreateCartItemInMessage(testUserId, createdCart.cartId, Ids.ItemId(1L), 1)

                shouldNotThrow<TaskException> {
                    val response = createCartItemUseCase.create(inMsg)
                    println("created : $response")

                    with(response) {
                        shouldNotBeNull()
                        cartId shouldBe inMsg.cartId
                        cnt shouldBe inMsg.cnt
                    }
                }
            }
        }
        context("실패 케이스") {
            it("추가 실패 - 존재하지 않는 상품 ID") {
                val notFoundItemId = Ids.ItemId(1000L)
                val inMsg = CreateCartItemInMessage(testUserId, createdCart.cartId, notFoundItemId, 1)

                shouldThrow<ItemNotFoundException> {
                    createCartItemUseCase.create(inMsg)
                }
            }
            it("추가 실패 - 유저 ID 에 해당하는 장바구니가 없음") {
                val invalidUser = Ids.UserId("nothing")
                val inMsg = CreateCartItemInMessage(invalidUser, createdCart.cartId, Ids.ItemId(1L), 1)

                shouldThrow<CartNotFoundException> {
                    createCartItemUseCase.create(inMsg)
                }
            }
        }
    }

    describe("장바구니 상품 제거") {
        context("성공 케이스") {
            it("장바구니 상품 제거 성공") {
                val createCartItemInMsg = CreateCartItemInMessage(testUserId, createdCart.cartId, Ids.ItemId(1L), 1)
                val createOutMsg = createCartItemUseCase.create(createCartItemInMsg)

                val deleteItemIds = listOf(createOutMsg.cartItemId)
                val deleteInMsg = DeleteCartItemsInMessage(testUserId, createdCart.cartId, deleteItemIds)

                shouldNotThrow<TaskException> {
                    val deleteResponse = deleteCartItemUseCase.delete(deleteInMsg)

                    deleteResponse.cartId shouldBe deleteInMsg.cartId
                    deleteResponse.cartItems shouldNotContainAnyOf deleteItemIds
                }
            }
        }
        context("실패 케이스") {
            it("제거 실패 - 존재하지 않는 사용자 장바구니") {
                val createCartItemInMsg = CreateCartItemInMessage(testUserId, createdCart.cartId, Ids.ItemId(1L), 1)
                val createOutMsg = createCartItemUseCase.create(createCartItemInMsg)

                val invalidUser = Ids.UserId("invalidUser")
                val deleteItemIds = listOf(createOutMsg.cartItemId)
                val deleteInMsg = DeleteCartItemsInMessage(invalidUser, createdCart.cartId, deleteItemIds)

                shouldThrow<CartNotFoundException> {
                    deleteCartItemUseCase.delete(deleteInMsg)
                }
            }
        }
    }
})
