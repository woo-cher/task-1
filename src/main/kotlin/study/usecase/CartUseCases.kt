package org.example.study.usecase

interface CartUseCases {
    interface Create<I, O> { fun create(inMsg: I): O }
    interface Get<I, O> { fun get(inMsg: I): O }
}
