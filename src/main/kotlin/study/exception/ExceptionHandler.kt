package org.example.study.exception

object ExceptionHandler {
    inline fun <T> handle(block: () -> T): T {
        return try {
            block()
        } catch (e: CartAlreadyExistException) {
            println(e.toErrorResponse())
            throw e
        }
    }
}
