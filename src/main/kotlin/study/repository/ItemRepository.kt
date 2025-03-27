package org.example.study.repository

import org.example.study.domain.entity.Item
import org.example.study.domain.id.Ids
import org.example.study.exception.ItemNotFoundException
import org.example.study.exception.errors.TaskErrors
import org.example.study.repository.item.dto.GetItemDto
import org.example.study.repository.item.vo.GetItemVo
import org.example.study.service.generator.Generator

class ItemRepository(
    private var items: Map<Ids.ItemId, Item> = Generator.generateItems(),
    private val error: TaskErrors = TaskErrors.ITEM_NOT_FOUND
) {
    fun findById(dto: GetItemDto): GetItemVo {
        val dbItem = items[dto.itemId] ?: throw ItemNotFoundException(error.code, error.messageWith(dto.itemId.id))
        return GetItemVo(dbItem.itemId, dbItem.name, dbItem.price, dbItem.fee)
    }
}
