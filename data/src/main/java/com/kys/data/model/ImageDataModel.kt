package com.kys.data.model

import com.kys.domain.model.ImageDomainModel
import kotlinx.serialization.Serializable

@Serializable
data class  ImageDataModel (
    val id: Int,
    val url: String
){
    fun toImageCardDomainModel(): ImageDomainModel {
        return ImageDomainModel(
            id = id,
            url = url
        )
    }
}