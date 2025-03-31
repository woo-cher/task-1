package org.example.study.usecase

interface CartItemUseCases {
    interface Create<I, O> { fun create(inMsg: I): O }
    interface Delete<I, O> { fun delete(inMsg: I): O }
    interface Update<I, O> { fun update(inMsg: I): O }
}
