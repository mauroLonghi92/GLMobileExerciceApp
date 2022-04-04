package com.example.glmobileexerciceapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.entitie.ItemEntity
import com.example.glmobileexerciceapp.util.Event
import org.koin.core.KoinComponent

class ItemDetailViewModel : ViewModel(), KoinComponent {

    private var itemDetailMutableLiveData: MutableLiveData<Event<ItemDetailData>> = MutableLiveData()
    val itemDetailLiveData: LiveData<Event<ItemDetailData>>
        get() {
            return itemDetailMutableLiveData
        }

    fun getItemInformation(item: ItemEntity) {

        itemDetailMutableLiveData.value =
            Event(
                ItemDetailData(
                    status = ItemDetailStatus.CHARGE_ITEM_INFORMATION,
                    data = item
                )
            )

    }
}

data class ItemDetailData(
    var status: ItemDetailStatus,
    var data: ItemEntity? = null
)

enum class ItemDetailStatus {
    CHARGE_ITEM_INFORMATION
}