package study.type.data

import study.type.InfraDataType
import study.type.id.Ids

data class ItemData(
    val itemId: Ids.ItemId,
    val name: String,
    val price: Long,
    val fee: Long
): InfraDataType
