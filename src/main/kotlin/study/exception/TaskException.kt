package org.example.study.exception

sealed class TaskException(
    errorCode: Int,
    message: String
): RuntimeException(message)
