package org.example.study.exception

class CartAlreadyExistException(
    errorCode: Int,
    errorMessage: String
): TaskException(errorCode, errorMessage)
