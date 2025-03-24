package org.example.study.domain.policy

import org.example.study.domain.entity.Cart
import org.example.study.domain.id.Ids

class CartPolicy {

    fun validateExistsOrThrow(
        cartSupplier: () -> Cart?,
        thrower: ExceptionThrower<Ids.UserId>
    ) {
        cartSupplier()?.let { thrower.invokeWith(it.userId) }
    }
}
