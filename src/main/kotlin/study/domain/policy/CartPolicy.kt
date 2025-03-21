package org.example.study.domain.policy

import org.example.study.domain.entity.Cart
import org.example.study.domain.id.Ids

class CartPolicy {
    fun canCreateCart(
        cartSupplier: () -> Cart?,
        thrower: (Ids.UserId) -> Unit
    ) {
        cartSupplier()?.let { thrower.invoke(it.userId) }
    }
}
