package org.example.study.exception

class ItemNotFoundException(
    errorCode: Int,
    errorMessage: String
): TaskException(errorCode, errorMessage)
