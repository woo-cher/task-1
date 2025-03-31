package org.example.study.usecase.handler

import org.example.study.exception.TaskException

sealed class ExceptionHandler {
    protected inline fun <T> handle(block: () -> T): T {
        return try {
            block()
        } catch (e: TaskException) {
            println(e.toErrorResponse())
            throw e
        }
    }
}
