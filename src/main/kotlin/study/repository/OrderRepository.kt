package org.example.study.repository

import org.example.study.domain.entity.Order

class OrderRepository(
    private var orders: MutableList<Order> = mutableListOf()
) {

    fun createOrder() {
        TODO("calculate price")
    }
}
