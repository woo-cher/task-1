package org.example.study.domain.entity

import org.example.study.domain.id.Ids

data class Item(
    val itemId: Ids.ItemId,
    val name: String,
    val price: Long,
    val fee: Long
)
