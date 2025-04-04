package study

interface OrderUseCases {
    interface Create<I, O>: UseCase<I, O>
}
