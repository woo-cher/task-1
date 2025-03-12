package org.example.study.repository

import org.example.study.domain.Generator
import org.example.study.domain.entity.Item
import org.example.study.domain.id.Ids

class ItemRepository(
    private var items: MutableMap<Ids.ItemId, Item> = Generator.generateItems()
) {
    fun findAll() = items
}
