package com.kys.mindteck.model

import com.kys.domain.model.DataResponseDomainModel

data class UiResponse(
    val images: List<UiImage>,
    val items: List<UiItem>
) {
    companion object{
        fun toUiResponse(domainResponse: DataResponseDomainModel): UiResponse {
            return UiResponse(
                images = domainResponse.images.map { UiImage.toUiModel(it) },
                items = domainResponse.items.map { UiItem.toUiModel(it) }
            )
        }
    }
}
