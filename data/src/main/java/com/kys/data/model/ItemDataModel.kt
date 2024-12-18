package com.kys.data.model

import com.kys.domain.model.ItemDomainModel
import kotlinx.serialization.Serializable

@Serializable
data class ItemDataModel(
    val id: Int,
    val url : String,
    val title: String,
    val subtitle: String,
) {
    fun toItemDomainModel(): ItemDomainModel {
        return ItemDomainModel(
            id = id,
            url = url,
            title = title,
            subtitle = subtitle
        )
    }
}
