package org.example.study.usecase

interface CartUseCases {
    interface Create<I, O>: UseCase<I, O>
    interface Get<I, O>: UseCase<I, O>
}
