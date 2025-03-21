package org.example.study.repository.cart.dto

import org.example.study.domain.id.Ids

data class GetCartByUserDto(
    val userId: Ids.UserId
)
