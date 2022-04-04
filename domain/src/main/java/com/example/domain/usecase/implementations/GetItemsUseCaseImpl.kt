package com.example.domain.usecase.implementations

import com.example.domain.entitie.ItemEntity
import com.example.domain.service.ItemsService
import com.example.domain.usecase.GetItemsUseCase
import com.example.domain.util.Result

class GetItemsUseCaseImpl(private val itemsService: ItemsService) : GetItemsUseCase {
    override fun getItems(): Result<List<ItemEntity>> {
//        return Result.Success(data = listOf(ItemEntity("ok", "", "")))
        return itemsService.getItems()
    }

}