package com.kys.mindteck.model

import com.kys.domain.model.ImageDomainModel

data class UiImage (
    val id: Int,
    val url: String
){
    companion object{
        fun toUiModel(domainImage: ImageDomainModel): UiImage {
            return UiImage(
                id = domainImage.id,
                url = domainImage.url
            )
        }
    }
}
