package com.kys.data.model

import com.kys.domain.model.DataResponseDomainModel

data class DataResponse(
    val images: List<ImageDataModel>,
    val items: List<ItemDataModel>,
) {
    fun toDataResponseDomainModel(): DataResponseDomainModel {
        return DataResponseDomainModel(
            images = images.map { it.toImageCardDomainModel() },
            items = items.map { it.toItemDomainModel() }
        )
    }
}
