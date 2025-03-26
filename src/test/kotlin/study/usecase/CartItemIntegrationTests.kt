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
import org.example.study.usecase.cart_item.UpdateCartItemUseCase
import org.example.study.usecase.cart_item.in_msg.CreateCartItemInMessage
import org.example.study.usecase.cart_item.in_msg.DeleteCartItemsInMessage
import org.example.study.usecase.cart_item.in_msg.UpdateCartItemInMessage
import org.example.study.usecase.cart_item.out_msg.CreateCartItemOutMessage
import org.example.study.usecase.cart_item.out_msg.DeleteCartItemsOutMessage
import org.example.study.usecase.cart_item.out_msg.UpdateCartItemOutMessage

@DisplayName("장바구니 상품 통합 테스트")
class CartItemIntegrationTests: DescribeSpec({
    lateinit var createdCart: Cart
    lateinit var createCartUseCase: CartUseCases.CreateCartUseCase<CreateCartInMessage, CreateCartOutMessage>

    lateinit var createUseCase: CartItemUseCases.CreateCartItemUseCase<CreateCartItemInMessage, CreateCartItemOutMessage>
    lateinit var deleteUseCase: CartItemUseCases.DeleteCartItemsUseCase<DeleteCartItemsInMessage, DeleteCartItemsOutMessage>
    lateinit var updateUseCase: CartItemUseCases.UpdateCartItemUseCase<UpdateCartItemInMessage, UpdateCartItemOutMessage>

    val testUserId = Ids.UserId("testUser")

    beforeEach {
        val cartRepository = CartRepository()
        val cartPolicy = CartPolicy()

        createCartUseCase = CreateCartUseCase(cartRepository, cartPolicy)
        createUseCase = CreateCartItemUseCase(cartRepository, ItemRepository())
        deleteUseCase = DeleteCartItemsUseCase(cartRepository)
        updateUseCase = UpdateCartItemUseCase(cartRepository)

        val createInMsg = CreateCartInMessage(testUserId)
        createdCart = createCartUseCase.create(createInMsg).cart
    }

    describe("장바구니 상품 추가") {
        context("성공 케이스") {
            it("장바구니 상품 추가 성공") {
                val inMsg = CreateCartItemInMessage(testUserId, createdCart.cartId, Ids.ItemId(1L), 1)

                shouldNotThrow<TaskException> {
                    val response = createUseCase.create(inMsg)
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
                    createUseCase.create(inMsg)
                }
            }
            it("추가 실패 - 유저 ID 에 해당하는 장바구니가 없음") {
                val invalidUser = Ids.UserId("nothing")
                val inMsg = CreateCartItemInMessage(invalidUser, createdCart.cartId, Ids.ItemId(1L), 1)

                shouldThrow<CartNotFoundException> {
                    createUseCase.create(inMsg)
                }
            }
        }
    }

    describe("장바구니 상품 제거") {
        context("성공 케이스") {
            it("장바구니 상품 제거 성공") {
                val createCartItemInMsg = CreateCartItemInMessage(testUserId, createdCart.cartId, Ids.ItemId(1L), 1)
                val createOutMsg = createUseCase.create(createCartItemInMsg)

                val deleteItemIds = listOf(createOutMsg.cartItemId)
                val deleteInMsg = DeleteCartItemsInMessage(testUserId, createdCart.cartId, deleteItemIds)

                shouldNotThrow<TaskException> {
                    val deleteResponse = deleteUseCase.delete(deleteInMsg)

                    deleteResponse.cartId shouldBe deleteInMsg.cartId
                    deleteResponse.cartItems shouldNotContainAnyOf deleteItemIds
                }
            }
        }
        context("실패 케이스") {
            it("제거 실패 - 존재하지 않는 사용자 장바구니") {
                val createCartItemInMsg = CreateCartItemInMessage(testUserId, createdCart.cartId, Ids.ItemId(1L), 1)
                val createOutMsg = createUseCase.create(createCartItemInMsg)

                val invalidUser = Ids.UserId("invalidUser")
                val deleteItemIds = listOf(createOutMsg.cartItemId)
                val deleteInMsg = DeleteCartItemsInMessage(invalidUser, createdCart.cartId, deleteItemIds)

                shouldThrow<CartNotFoundException> {
                    deleteUseCase.delete(deleteInMsg)
                }
            }
        }
    }

    describe("장바구니 상품 수량 변경") {
        val updateCnt = 2

        context("성공 케이스") {
            it("수량 변경 성공") {
                val createCartItemInMsg = CreateCartItemInMessage(testUserId, createdCart.cartId, Ids.ItemId(1L), 1)
                val createOutMsg = createUseCase.create(createCartItemInMsg)
                val updateInMsg = UpdateCartItemInMessage(testUserId, createdCart.cartId, createOutMsg.cartItemId, updateCnt)

                shouldNotThrow<TaskException> {
                    val updateResponse = updateUseCase.update(updateInMsg)

                    with(updateResponse) {
                        cartId shouldBe updateInMsg.cartId
                        cartItemId shouldBe updateInMsg.cartItemId
                        cnt shouldBe updateCnt
                    }
                }
            }
        }
        context("실패 케이스") {
            it("수량 변경 실패 - 존재하지 않는 사용자 장바구니") {
                val createCartItemInMsg = CreateCartItemInMessage(testUserId, createdCart.cartId, Ids.ItemId(1L), 1)
                val createOutMsg = createUseCase.create(createCartItemInMsg)
                val invalidUser = Ids.UserId("invalid")

                val updateInMsg = UpdateCartItemInMessage(invalidUser, createdCart.cartId, createOutMsg.cartItemId, updateCnt)

                shouldThrow<CartNotFoundException> {
                    updateUseCase.update(updateInMsg)
                }
            }
        }
    }
})
