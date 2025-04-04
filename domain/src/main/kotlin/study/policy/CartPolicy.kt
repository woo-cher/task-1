package study.policy

import study.persistence.Cart
import study.type.id.Ids

class CartPolicy {

    fun validateExistsOrThrow(
        cartSupplier: () -> Cart?,
        thrower: ExceptionThrower<Ids.UserId>
    ) {
        cartSupplier()?.let { thrower.invokeWith(it.userId) }
    }
}
