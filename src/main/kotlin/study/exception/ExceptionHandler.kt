package org.example.study.exception

// todo) handle { .. } 블럭으로 계쏙 써야해서 번거롭다
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
