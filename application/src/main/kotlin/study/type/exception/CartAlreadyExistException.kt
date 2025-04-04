package study.type.exception

class CartAlreadyExistException(
    errorCode: Int,
    errorMessage: String
): TaskException(errorCode, errorMessage)
