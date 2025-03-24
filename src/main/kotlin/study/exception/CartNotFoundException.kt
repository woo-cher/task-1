package org.example.study.exception

class CartNotFoundException(
    errorCode: Int,
    errorMessage: String
): TaskException(errorCode, errorMessage)
