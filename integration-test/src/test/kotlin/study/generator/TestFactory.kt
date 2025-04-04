package study.generator

import study.CartRepository
import study.Generator
import study.ItemRepository
import study.OrderRepository
import study.cart.CreateCartUseCase
import study.cart.GetCartByUserIdUseCase
import study.cart.in_msg.CreateCartInMessage
import study.cart.in_msg.GetCartByUserInMessage
import study.cart.out_msg.CreateCartOutMessage
import study.cart.out_msg.GetCartByUserOutMessage
import study.cart_item.CreateCartItemUseCase
import study.cart_item.DeleteCartItemsUseCase
import study.cart_item.UpdateCartItemUseCase
import study.cart_item.in_msg.CreateCartItemInMessage
import study.cart_item.in_msg.DeleteCartItemsInMessage
import study.cart_item.in_msg.UpdateCartItemInMessage
import study.cart_item.out_msg.CreateCartItemOutMessage
import study.cart_item.out_msg.DeleteCartItemsOutMessage
import study.cart_item.out_msg.UpdateCartItemOutMessage
import study.handler.UseCaseHandlerProxy
import study.order.CreateOrderUseCase
import study.order.in_msg.CreateOrderInMessage
import study.order.out_msg.CreateOrderOutMessage
import study.policy.CartPolicy
import study.type.id.Ids

object TestFactory {
    private const val TEST_USER = "testUser"
    private const val TEST_INVALID_USER = "invalidTestUser"
    const val START_ID = 1L

    val testUser = Ids.UserId(TEST_USER)
    val invalidTestUser = Ids.UserId(TEST_INVALID_USER)

    fun invalidId(): Long = Generator.testSize() + 10L
    fun Long.toItemId() = Ids.ItemId(this)
    fun Long.toCartId() = Ids.CartId(this)

    fun cartPolicy() = CartPolicy()

    fun createCartProxy(cartRepository: CartRepository, cartPolicy: CartPolicy): UseCaseHandlerProxy<CreateCartInMessage, CreateCartOutMessage> =
        UseCaseHandlerProxy(CreateCartUseCase(cartRepository, cartPolicy))

    fun getCartProxy(cartRepository: CartRepository): UseCaseHandlerProxy<GetCartByUserInMessage, GetCartByUserOutMessage> =
        UseCaseHandlerProxy(GetCartByUserIdUseCase(cartRepository))

    fun createCartItemProxy(cartRepository: CartRepository, itemRepository: ItemRepository): UseCaseHandlerProxy<CreateCartItemInMessage, CreateCartItemOutMessage> =
        UseCaseHandlerProxy(CreateCartItemUseCase(cartRepository, itemRepository))

    fun deleteCartItemProxy(cartRepository: CartRepository): UseCaseHandlerProxy<DeleteCartItemsInMessage, DeleteCartItemsOutMessage> =
        UseCaseHandlerProxy(DeleteCartItemsUseCase(cartRepository))

    fun updateCartItemProxy(cartRepository: CartRepository): UseCaseHandlerProxy<UpdateCartItemInMessage, UpdateCartItemOutMessage> =
        UseCaseHandlerProxy(UpdateCartItemUseCase(cartRepository))

    fun createOrderProxy(orderRepository: OrderRepository, cartRepository: CartRepository): UseCaseHandlerProxy<CreateOrderInMessage, CreateOrderOutMessage> =
        UseCaseHandlerProxy(CreateOrderUseCase(orderRepository, cartRepository))

    fun createCartItemInMsg(
        userId: Ids.UserId = testUser,
        itemId: Ids.ItemId = START_ID.toItemId(),
        cartId: Ids.CartId
    ) = CreateCartItemInMessage(userId, cartId, itemId, 1)

    fun deleteCartItemInMsg(
        userId: Ids.UserId = testUser,
        cartId: Ids.CartId,
        itemIds: List<Ids.CartItemId>
    ) = DeleteCartItemsInMessage(userId, cartId, itemIds)
}
