package org.example.study.usecase

import org.example.study.exception.ExceptionHandler

class UseCaseHandlerProxy<I, O>(
    private val useCase: UseCase<I, O>,
) {
    fun execute(inMsg: I): O {
        return ExceptionHandler.handle {
            useCase.execute(inMsg)
        }
    }
}
