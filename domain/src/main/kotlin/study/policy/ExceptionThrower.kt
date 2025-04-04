package study.policy

fun interface ExceptionThrower<T> {
    fun invokeWith(value: T)
}
