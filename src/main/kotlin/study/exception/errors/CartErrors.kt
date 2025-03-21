package org.example.study.exception.errors

enum class CartErrors(
    val code: Int,
    val messageOperator: (Any) -> String,
) {
    // example
    CART_ALREADY_EXIST(409, { userId -> "Cart with userId $userId already exists." })
}
