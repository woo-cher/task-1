package org.example.study.usecase.cart.in_msg

import org.example.study.domain.id.Ids

data class CreateCartInMessage(
    val userId: Ids.UserId
)
