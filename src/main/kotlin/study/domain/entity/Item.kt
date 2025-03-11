package org.example.study.domain.entity

import org.example.study.domain.enums.Fee
import org.example.study.domain.id.Ids

data class Item(
    val id: Ids.ItemId,
    val name: String,
    val price: Int,
    val fee: Fee
)
