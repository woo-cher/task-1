package org.example.study.exception

object ExceptionHandler {
    inline fun <T> handle(block: () -> T): T {
        return try {
            block()
        } catch (e: TaskException) {
            println(e.toErrorResponse())
            throw e
        }
    }
}
