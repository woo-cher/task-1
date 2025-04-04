package study.type.exception

sealed class TaskException(
    private val errorCode: Int,
    private val errorMessage: String
): RuntimeException(errorMessage) {

    fun toErrorResponse(): ErrorResponse {
        return ErrorResponse(errorCode, errorMessage)
    }

    data class ErrorResponse(
        val errorCode: Int,
        val errorMessage: String
    ) {
        override fun toString(): String {
            return "errorCode: $errorCode,\nerrorMessage: $errorMessage"
        }
    }
}
