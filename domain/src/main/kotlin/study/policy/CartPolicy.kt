package study.policy

import study.type.data.CartData
import study.type.id.Ids

class CartPolicy {

    fun validateExistsOrThrow(
        cartSupplier: () -> CartData?,
        thrower: ExceptionThrower<Ids.UserId>
    ) {
        cartSupplier()?.let { thrower.invokeWith(it.userId) }
    }
}
