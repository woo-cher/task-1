package study.generator

import org.example.study.domain.id.Ids
import org.example.study.domain.policy.CartPolicy
import org.example.study.repository.CartRepository
import org.example.study.repository.ItemRepository
import org.example.study.service.generator.Generator
import org.example.study.usecase.CartItemUseCases
import org.example.study.usecase.CartUseCases
import org.example.study.usecase.cart.CreateCartUseCase
import org.example.study.usecase.cart.GetCartByUserIdUseCase
import org.example.study.usecase.cart.in_msg.CreateCartInMessage
import org.example.study.usecase.cart.in_msg.GetCartByUserInMessage
import org.example.study.usecase.cart.out_msg.CreateCartOutMessage
import org.example.study.usecase.cart.out_msg.GetCartByUserOutMessage
import org.example.study.usecase.cart_item.CreateCartItemUseCase
import org.example.study.usecase.cart_item.DeleteCartItemsUseCase
import org.example.study.usecase.cart_item.UpdateCartItemUseCase
import org.example.study.usecase.cart_item.in_msg.CreateCartItemInMessage
import org.example.study.usecase.cart_item.in_msg.DeleteCartItemsInMessage
import org.example.study.usecase.cart_item.in_msg.UpdateCartItemInMessage
import org.example.study.usecase.cart_item.out_msg.CreateCartItemOutMessage
import org.example.study.usecase.cart_item.out_msg.DeleteCartItemsOutMessage
import org.example.study.usecase.cart_item.out_msg.UpdateCartItemOutMessage

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

    fun createCartUseCase(cartRepository: CartRepository, cartPolicy: CartPolicy): CartUseCases.Create<CreateCartInMessage, CreateCartOutMessage> =
        CreateCartUseCase(cartRepository, cartPolicy)

    fun getCartUseCase(cartRepository: CartRepository): CartUseCases.Get<GetCartByUserInMessage, GetCartByUserOutMessage> =
        GetCartByUserIdUseCase(cartRepository)

    fun createCartItemUseCase(cartRepository: CartRepository, itemRepository: ItemRepository): CartItemUseCases.Create<CreateCartItemInMessage, CreateCartItemOutMessage> =
        CreateCartItemUseCase(cartRepository, itemRepository)

    fun deleteCartItemUseCase(cartRepository: CartRepository): CartItemUseCases.Delete<DeleteCartItemsInMessage, DeleteCartItemsOutMessage> =
        DeleteCartItemsUseCase(cartRepository)

    fun updateCartItemUseCase(cartRepository: CartRepository): CartItemUseCases.Update<UpdateCartItemInMessage, UpdateCartItemOutMessage> =
        UpdateCartItemUseCase(cartRepository)

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
