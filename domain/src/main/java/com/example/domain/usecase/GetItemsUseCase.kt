package com.example.domain.usecase

import com.example.domain.entitie.ItemEntity
import com.example.domain.util.Result
import org.koin.core.KoinComponent

interface GetItemsUseCase : KoinComponent {
    fun getItems(): Result<List<ItemEntity>>
}