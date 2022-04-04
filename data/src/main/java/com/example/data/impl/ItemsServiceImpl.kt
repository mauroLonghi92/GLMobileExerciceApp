package com.example.data.impl

import com.example.data.generator.ServiceGenerator
import com.example.data.response.toItemListEntity
import com.example.data.service.ServiceApi
import com.example.domain.entitie.ItemEntity
import com.example.domain.service.ItemsService
import com.example.domain.util.Result

class ItemsServiceImpl : ItemsService {

    private val api = ServiceGenerator()
    override fun getItems(): Result<List<ItemEntity>> {
        try {
            val callResponse = api.createService(ServiceApi::class.java).getItems()
            val response = callResponse.execute()
            if (response.isSuccessful)
                return Result.Success(data = response.body()?.toItemListEntity() ?: emptyList())

        } catch (e: Exception) {
            return Result.Failure(Exception(ERROR_EXCEPTION_MESSAGE))
        }
        return Result.Failure(Exception(ERROR_NOT_ITEM_MESSAGE))
    }

    companion object {
        private const val ERROR_EXCEPTION_MESSAGE = "Algo inesperado sucedió"
        private const val ERROR_NOT_ITEM_MESSAGE = "No se encontraron Items"
    }
}