package org.example.study.usecase

interface CartUseCases {
    interface CreateCartUseCase<I, O> { fun create(inMsg: I): O }
    interface GetCartUseCase<I, O> { fun get(inMsg: I): O }
}
