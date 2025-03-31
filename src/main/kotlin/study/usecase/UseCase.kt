package org.example.study.usecase

sealed interface UseCase<I, O> {
    fun execute(inMsg: I): O
}
