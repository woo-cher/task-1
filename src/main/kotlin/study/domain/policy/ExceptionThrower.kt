package org.example.study.domain.policy

fun interface ExceptionThrower<T> {
    fun invokeWith(value: T)
}
