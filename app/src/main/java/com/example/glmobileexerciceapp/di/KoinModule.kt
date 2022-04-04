package com.example.glmobileexerciceapp.di

import com.example.glmobileexerciceapp.viewmodel.ItemDetailViewModel
import com.example.glmobileexerciceapp.viewmodel.ItemsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { ItemsViewModel(get()) }
    viewModel { ItemDetailViewModel() }
}