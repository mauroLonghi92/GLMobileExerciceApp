package com.example.domain.service

import com.example.domain.entitie.ItemEntity
import com.example.domain.util.Result

interface ItemsService {

    fun getItems(): Result<List<ItemEntity>>
}