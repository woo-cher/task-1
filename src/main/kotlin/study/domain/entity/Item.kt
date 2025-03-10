package org.example.study.domain.entity

import org.example.study.domain.enums.Fee

data class Item(
    val id: Long,
    val name: String,
    val price: Int,
    val fee: Fee
)
