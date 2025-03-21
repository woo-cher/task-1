package org.example.study.exception

class CartAlreadyExistException(
    errorCode: Int,
    message: String
): TaskException(errorCode, message)
