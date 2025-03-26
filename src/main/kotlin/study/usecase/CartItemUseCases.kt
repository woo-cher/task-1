package org.example.study.usecase

interface CartItemUseCases {
    interface CreateCartItemUseCase<I, O> { fun create(inMsg: I): O }
    interface DeleteCartItemsUseCase<I, O> { fun delete(inMsg: I): O }
    interface UpdateCartItemUseCase<I, O> { fun update(inMsg: I): O }
}
