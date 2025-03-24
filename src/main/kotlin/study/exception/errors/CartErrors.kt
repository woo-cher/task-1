package org.example.study.exception.errors

enum class CartErrors(
    val code: Int,
    private val messageOperator: (Any) -> String,
) {
    CART_ALREADY_EXIST(409, { userId -> "Cart with userId: $userId is already exists." });

    fun messageWith(value: Any): String {
        return messageOperator(value)
    }
}
