package com.kys.data.repository

import com.kys.data.model.DataResponse
import com.kys.data.model.ImageDataModel
import com.kys.data.model.ItemDataModel
import com.kys.domain.model.DataResponseDomainModel
import com.kys.domain.network.NetworkService
import com.kys.domain.network.ResultWrapper
import com.kys.domain.repository.MindteckRepository
import kotlinx.coroutines.delay

class MindteckRepositoryImpl(
    private val networkService: NetworkService
): MindteckRepository {
    override suspend fun getDynamicInformation(): ResultWrapper<DataResponseDomainModel> {
        // return networkService.getDynamicInformation()
        val images = listOf<ImageDataModel>(
            ImageDataModel(1,"https://images.unsplash.com/photo-1506748686214-e9df14d4d9d0"),
            ImageDataModel(2,"https://images.unsplash.com/photo-1493612276216-ee3925520721"),
            ImageDataModel(3,"https://images.unsplash.com/photo-1507525428034-b723cf961d3e"),
            ImageDataModel(4,"https://images.unsplash.com/photo-1727270921836-3d51d25d9e33"),
            ImageDataModel(5,"https://images.unsplash.com/photo-1526772662000-3f88f10405ff"),
        )
        val items = listOf<ItemDataModel>(
            ItemDataModel(1, "https://images.unsplash.com/photo-1506748686214-e9df14d4d9d0", "List item title", "List item subtitle"),
            ItemDataModel(2, "https://images.unsplash.com/photo-1493612276216-ee3925520721", "List item title", "List item subtitle"),
            ItemDataModel(3, "https://images.unsplash.com/photo-1507525428034-b723cf961d3e", "List item title", "List item subtitle"),
            ItemDataModel(4, "https://images.unsplash.com/photo-1727270921836-3d51d25d9e33", "List item title", "List item subtitle"),
            ItemDataModel(5, "https://images.unsplash.com/photo-1526772662000-3f88f10405ff", "List item title", "List item subtitle"),
        )
        val dataResponse = DataResponse(images, items)
        return ResultWrapper.Success(dataResponse.toDataResponseDomainModel())
    }
}