package org.example.study.repository.cart.dto

import org.example.study.domain.id.Ids

data class CreateCartDto(
    val userId: Ids.UserId
)
