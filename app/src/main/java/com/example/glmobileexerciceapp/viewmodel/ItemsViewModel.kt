package com.example.glmobileexerciceapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entitie.ItemEntity
import com.example.domain.usecase.GetItemsUseCase
import com.example.domain.util.Result
import com.example.glmobileexerciceapp.util.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ItemsViewModel(private val getItemsUseCase: GetItemsUseCase) : ViewModel() {

    private val itemsMutableLiveData = MutableLiveData<Event<ItemsData>>()
    val itemsLiveData: LiveData<Event<ItemsData>>
        get() {
            return itemsMutableLiveData
        }

    fun fetchItems(): Job = viewModelScope.launch {
        itemsMutableLiveData.postValue(Event(ItemsData(status = ItemsStatus.LOADING)))
        withContext(Dispatchers.IO) { getItemsUseCase.getItems() }.let { result ->
            when (result) {
                is Result.Success -> {
                    itemsMutableLiveData.postValue(
                        Event(
                            ItemsData(
                                status = ItemsStatus.SUCCESS_DATA,
                                data = result.data
                            )
                        )
                    )
                }
                is Result.Failure -> {
                    itemsMutableLiveData.postValue(
                        Event(
                            ItemsData(
                                status = ItemsStatus.ERROR,
                                error = result.exception
                            )
                        )
                    )
                }
            }
        }
    }

    fun onItemClicked(item: ItemEntity) {
        itemsMutableLiveData.value = Event(ItemsData(status = ItemsStatus.OPEN_ITEM_DETAIL, data = item))
    }
}

data class ItemsData(
    var status: ItemsStatus,
    var data: Any? = null,
    var error: Exception? = null
)

enum class ItemsStatus {
    LOADING,
    SUCCESS_DATA,
    ERROR,
    OPEN_ITEM_DETAIL
}