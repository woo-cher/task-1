package org.example.study.exception.errors

enum class TaskErrors(
    val code: Int,
    private val messageOperator: (Any) -> String,
) {
    // cart errors
    CART_ALREADY_EXIST(409, { userId -> "Cart with userId: $userId is already exists." }),

    // item errors
    ITEM_NOT_FOUND(404, { itemId -> "Item with itemId: $itemId is not found."})
    ;

    fun messageWith(value: Any): String {
        return messageOperator(value)
    }
}
