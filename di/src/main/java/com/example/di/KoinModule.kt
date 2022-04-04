package com.example.di

import com.example.data.impl.ItemsServiceImpl
import com.example.domain.service.ItemsService
import com.example.domain.usecase.GetItemsUseCase
import com.example.domain.usecase.implementations.GetItemsUseCaseImpl
import org.koin.dsl.module

val serviceModule = module {

    single<ItemsService> { ItemsServiceImpl() }
}

val useCaseModule = module {

    single<GetItemsUseCase> { GetItemsUseCaseImpl(get()) }
}
