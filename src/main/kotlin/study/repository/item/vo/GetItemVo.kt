package org.example.study.repository.item.vo

import org.example.study.domain.id.Ids

data class GetItemVo(
    val itemId: Ids.ItemId,
    val name: String,
    val price: Long,
    val fee: Long
)
