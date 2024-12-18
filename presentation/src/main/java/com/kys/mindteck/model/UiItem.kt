package com.kys.mindteck.model

import com.kys.domain.model.ImageDomainModel
import com.kys.domain.model.ItemDomainModel

data class UiItem(
    val id: Int,
    val url : String,
    val title: String,
    val subtitle: String
) {
    companion object{
        fun toUiModel(domainItem: ItemDomainModel): UiItem {
            return UiItem(
                id = domainItem.id,
                url = domainItem.url,
                title = domainItem.title,
                subtitle = domainItem.subtitle,
            )
        }
    }
}
