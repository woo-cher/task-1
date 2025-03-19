package org.example.study.repository

import org.example.study.domain.entity.Item
import org.example.study.domain.id.Ids
import org.example.study.repository.item.dto.GetItemDto
import org.example.study.repository.item.vo.GetItemVo
import org.example.study.service.generator.ItemGenerator

class ItemRepository(
    private var items: Map<Ids.ItemId, Item> = ItemGenerator.generate()
) {
    fun findById(dto: GetItemDto): GetItemVo {
        val dbItem = items[dto.itemId] ?: throw IllegalArgumentException("Not found item : ${dto.itemId}")
        return GetItemVo(dbItem.itemId, dbItem.name, dbItem.price, dbItem.fee)
    }
}
