package study

interface CartItemUseCases {
    interface Create<I, O>: UseCase<I, O>
    interface Delete<I, O>: UseCase<I, O>
    interface Update<I, O>: UseCase<I, O>
}
