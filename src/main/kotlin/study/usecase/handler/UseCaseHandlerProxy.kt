package org.example.study.usecase.handler

import org.example.study.usecase.UseCase

class UseCaseHandlerProxy<I, O> (
    private val useCase: UseCase<I, O>,
): ExceptionHandler() {
    fun execute(inMsg: I): O {
        return handle {
            useCase.execute(inMsg)
        }
    }
}
