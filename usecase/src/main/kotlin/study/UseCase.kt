package study

sealed interface UseCase<I, O> {
    fun execute(inMsg: I): O
}
