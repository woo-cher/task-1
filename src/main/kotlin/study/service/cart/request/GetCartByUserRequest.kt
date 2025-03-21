package org.example.study.service.cart.request

import org.example.study.domain.id.Ids

data class GetCartByUserRequest(
    val userId: Ids.UserId
)
