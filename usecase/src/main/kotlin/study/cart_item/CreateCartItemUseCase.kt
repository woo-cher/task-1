package study.cart_item

import study.CartItemUseCases
import study.CartRepository
import study.ItemRepository
import study.cart_item.dto.CreateCartItemDto
import study.cart_item.in_msg.CreateCartItemInMessage
import study.cart_item.out_msg.CreateCartItemOutMessage
import study.cart_item.vo.CreateCartItemVo
import study.item.dto.GetItemDto
import study.item.vo.GetItemVo

class CreateCartItemUseCase(
    private val cartRepository: CartRepository,
    private val itemRepository: ItemRepository
): CartItemUseCases.Create<CreateCartItemInMessage, CreateCartItemOutMessage> {

    override fun execute(inMsg: CreateCartItemInMessage): CreateCartItemOutMessage {
        val item = inMsg.getItem()
        return inMsg.createCartItem(item.price).toOutMsg()
    }

    private fun CreateCartItemInMessage.getItem(): GetItemVo {
        val dto = GetItemDto(itemId)
        return itemRepository.findById(dto)
    }
    private fun CreateCartItemInMessage.createCartItem(price: Long): CreateCartItemVo {
        val dto = CreateCartItemDto(userId, cartId, itemId, price, cnt)
        return cartRepository.createCartItem(dto)
    }
    private fun CreateCartItemVo.toOutMsg() = CreateCartItemOutMessage(cartId, cartItemId, cnt)

}
