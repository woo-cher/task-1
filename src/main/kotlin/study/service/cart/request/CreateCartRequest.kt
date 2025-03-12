package org.example.study.service.cart.request

import org.example.study.domain.id.Ids

data class CreateCartRequest(
    val userId: Ids.UserId
)
