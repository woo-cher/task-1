package study.handler

import study.UseCase

class UseCaseHandlerProxy<I, O> (
    private val useCase: UseCase<I, O>,
): ExceptionHandler() {
    fun execute(inMsg: I): O {
        return handle {
            useCase.execute(inMsg)
        }
    }
}
