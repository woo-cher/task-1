package org.example.study.service.request

import org.example.study.domain.id.Ids

data class CreateCartRequest(
    val userId: Ids.UserId
)
