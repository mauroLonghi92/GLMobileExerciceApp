package com.example.data.response

import com.example.domain.entitie.ItemEntity
import com.google.gson.annotations.SerializedName

class ItemResponse(
    @SerializedName("title")
    val title: String? = "",
    @SerializedName("description")
    val description: String? = "",
    @SerializedName("image")
    val image: String? = "",
)

fun List<ItemResponse>.toItemListEntity(): List<ItemEntity> {

    return this?.map {

        it.toItemEntity()

    }.orEmpty()
}

fun ItemResponse.toItemEntity(): ItemEntity = ItemEntity(
    title = this.title.orEmpty(),
    description = this.description.orEmpty(),
    image = this.image.orEmpty()
)