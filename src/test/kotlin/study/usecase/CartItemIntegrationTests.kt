package study.usecase

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldNotContainAnyOf
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.example.study.domain.entity.Cart
import org.example.study.exception.CartNotFoundException
import org.example.study.exception.ItemNotFoundException
import org.example.study.exception.TaskException
import org.example.study.repository.CartRepository
import org.example.study.repository.ItemRepository
import org.example.study.usecase.CartItemUseCases
import org.example.study.usecase.CartUseCases
import org.example.study.usecase.cart.in_msg.CreateCartInMessage
import org.example.study.usecase.cart.out_msg.CreateCartOutMessage
import org.example.study.usecase.cart_item.in_msg.CreateCartItemInMessage
import org.example.study.usecase.cart_item.in_msg.DeleteCartItemsInMessage
import org.example.study.usecase.cart_item.in_msg.UpdateCartItemInMessage
import org.example.study.usecase.cart_item.out_msg.CreateCartItemOutMessage
import org.example.study.usecase.cart_item.out_msg.DeleteCartItemsOutMessage
import org.example.study.usecase.cart_item.out_msg.UpdateCartItemOutMessage
import study.generator.TestFactory
import study.generator.TestFactory.toItemId

@DisplayName("장바구니 상품 통합 테스트")
class CartItemIntegrationTests: DescribeSpec({
    lateinit var createdCart: Cart
    lateinit var createCartUseCase: CartUseCases.Create<CreateCartInMessage, CreateCartOutMessage>

    lateinit var createUseCase: CartItemUseCases.Create<CreateCartItemInMessage, CreateCartItemOutMessage>
    lateinit var deleteUseCase: CartItemUseCases.Delete<DeleteCartItemsInMessage, DeleteCartItemsOutMessage>
    lateinit var updateUseCase: CartItemUseCases.Update<UpdateCartItemInMessage, UpdateCartItemOutMessage>

    beforeEach {
        val cartRepository = CartRepository()
        val cartPolicy = TestFactory.cartPolicy()

        createCartUseCase = TestFactory.createCartUseCase(cartRepository, cartPolicy)
        createUseCase = TestFactory.createCartItemUseCase(cartRepository, ItemRepository())
        deleteUseCase = TestFactory.deleteCartItemUseCase(cartRepository)
        updateUseCase = TestFactory.updateCartItemUseCase(cartRepository)

        val createInMsg = CreateCartInMessage(TestFactory.testUser)
        createdCart = createCartUseCase.create(createInMsg).cart
    }

    describe("장바구니 상품 추가") {
        context("성공 케이스") {
            it("장바구니 상품 추가 성공") {
                val inMsg = TestFactory.createCartItemInMsg(cartId = createdCart.cartId)

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
                val inMsg = TestFactory.createCartItemInMsg(
                    itemId = TestFactory.invalidId().toItemId(),
                    cartId = createdCart.cartId
                )

                shouldThrow<ItemNotFoundException> {
                    createUseCase.create(inMsg)
                }
            }
            it("추가 실패 - 유저 ID 에 해당하는 장바구니가 없음") {
                val inMsg = TestFactory.createCartItemInMsg(
                    userId = TestFactory.invalidTestUser,
                    cartId = createdCart.cartId
                )

                shouldThrow<CartNotFoundException> {
                    createUseCase.create(inMsg)
                }
            }
        }
    }

    describe("장바구니 상품 제거") {
        context("성공 케이스") {
            it("장바구니 상품 제거 성공") {
                val createCartItemInMsg = TestFactory.createCartItemInMsg(cartId = createdCart.cartId)
                val createOutMsg = createUseCase.create(createCartItemInMsg)

                val deleteItemIds = listOf(createOutMsg.cartItemId)
                val deleteInMsg = TestFactory.deleteCartItemInMsg(
                    cartId = createdCart.cartId,
                    itemIds = deleteItemIds
                )

                shouldNotThrow<TaskException> {
                    val deleteResponse = deleteUseCase.delete(deleteInMsg)

                    deleteResponse.cartId shouldBe deleteInMsg.cartId
                    deleteResponse.cartItems shouldNotContainAnyOf deleteItemIds
                }
            }
        }
        context("실패 케이스") {
            it("제거 실패 - 존재하지 않는 사용자 장바구니") {
                val createCartItemInMsg = TestFactory.createCartItemInMsg(cartId = createdCart.cartId)
                val createOutMsg = createUseCase.create(createCartItemInMsg)

                val deleteItemIds = listOf(createOutMsg.cartItemId)
                val deleteInMsg = TestFactory.deleteCartItemInMsg(
                    TestFactory.invalidTestUser,
                    createdCart.cartId,
                    deleteItemIds
                )

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
                val createCartItemInMsg = TestFactory.createCartItemInMsg(cartId = createdCart.cartId)
                val createOutMsg = createUseCase.create(createCartItemInMsg)
                val updateInMsg = UpdateCartItemInMessage(
                    TestFactory.testUser,
                    createdCart.cartId,
                    createOutMsg.cartItemId,
                    updateCnt
                )

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
                val createCartItemInMsg = TestFactory.createCartItemInMsg(cartId = createdCart.cartId)
                val createOutMsg = createUseCase.create(createCartItemInMsg)

                val updateInMsg = UpdateCartItemInMessage(
                    TestFactory.invalidTestUser,
                    createdCart.cartId,
                    createOutMsg.cartItemId,
                    updateCnt
                )

                shouldThrow<CartNotFoundException> {
                    updateUseCase.update(updateInMsg)
                }
            }
        }
    }
})
