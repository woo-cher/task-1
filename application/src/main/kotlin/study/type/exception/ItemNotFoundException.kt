package study.type.exception

class ItemNotFoundException(
    errorCode: Int,
    errorMessage: String
): TaskException(errorCode, errorMessage)
