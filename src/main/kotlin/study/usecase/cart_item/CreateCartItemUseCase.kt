package org.example.study.usecase.cart_item

import org.example.study.exception.ExceptionHandler
import org.example.study.repository.CartRepository
import org.example.study.repository.ItemRepository
import org.example.study.repository.cart_item.dto.CreateCartItemDto
import org.example.study.repository.cart_item.vo.CreateCartItemVo
import org.example.study.repository.item.dto.GetItemDto
import org.example.study.repository.item.vo.GetItemVo
import org.example.study.usecase.CartItemUseCases
import org.example.study.usecase.cart_item.in_msg.CreateCartItemInMessage
import org.example.study.usecase.cart_item.out_msg.CreateCartItemOutMessage

class CreateCartItemUseCase(
    private val cartRepository: CartRepository,
    private val itemRepository: ItemRepository
): CartItemUseCases.Create<CreateCartItemInMessage, CreateCartItemOutMessage> {

    override fun create(inMsg: CreateCartItemInMessage): CreateCartItemOutMessage {
        ExceptionHandler.handle {
            val item = inMsg.getItem()
            return inMsg.createCartItem(item.price).toOutMsg()
        }
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
