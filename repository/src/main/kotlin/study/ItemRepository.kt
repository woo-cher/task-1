package study

import study.item.dto.GetItemDto
import study.item.vo.GetItemVo
import study.persistence.Item
import study.type.exception.ItemNotFoundException
import study.type.exception.errors.TaskErrors
import study.type.id.Ids

class ItemRepository(
    private var items: Map<Ids.ItemId, Item> = Generator.generateItems(),
    private val error: TaskErrors = TaskErrors.ITEM_NOT_FOUND
) {
    fun findById(dto: GetItemDto): GetItemVo {
        val dbItem = items[dto.itemId] ?: throw ItemNotFoundException(error.code, error.messageWith(dto.itemId.id))
        return GetItemVo(dbItem.itemId, dbItem.name, dbItem.price, dbItem.fee)
    }
}
