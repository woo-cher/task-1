package study.persistence

import study.type.InfraDataType

interface Persistence<T: InfraDataType> {
    fun toData(): T
    fun List<CartItem>.mapToData() = map { it.toData() }.toList()
}

