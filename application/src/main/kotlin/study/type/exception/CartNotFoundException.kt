package study.type.exception

class CartNotFoundException(
    errorCode: Int,
    errorMessage: String
): TaskException(errorCode, errorMessage)
