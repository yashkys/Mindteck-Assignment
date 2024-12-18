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
        delay(500L)
        // return networkService.getDynamicInformation()
        val images = listOf<ImageDataModel>(
            ImageDataModel(1,"https://next-images.123rf.com/index/_next/image/?url=https://assets-cdn.123rf.com/index/static/assets/top-section-bg.jpeg&w=3840&q=75"),
            ImageDataModel(2,"https://t3.ftcdn.net/jpg/09/04/72/88/360_F_904728821_ZFugpaa4Q3ALJ4peHaHGlZvZb4P99d8W.jpg"),
            ImageDataModel(3,"https://media.istockphoto.com/id/1403500817/photo/the-craggies-in-the-blue-ridge-mountains.jpg?s=612x612&w=0&k=20&c=N-pGA8OClRVDzRfj_9AqANnOaDS3devZWwrQNwZuDSk="),
            ImageDataModel(4,"https://t3.ftcdn.net/jpg/06/15/49/68/360_F_615496890_W34yB8VDE6n5pehb5QCt1ek5oEvRo9qA.jpg"),
            ImageDataModel(5,"https://t4.ftcdn.net/jpg/06/81/00/73/360_F_681007328_NjDzZuCqOjmMTXFZo5rqI2CwEgbk8fGk.jpg"),
        )
        val items = listOf<ItemDataModel>(
            ItemDataModel(1,"https://next-images.123rf.com/index/_next/image/?url=https://assets-cdn.123rf.com/index/static/assets/top-section-bg.jpeg&w=3840&q=75", "List item title", "List item subtitle"),
            ItemDataModel(2,"https://t3.ftcdn.net/jpg/09/04/72/88/360_F_904728821_ZFugpaa4Q3ALJ4peHaHGlZvZb4P99d8W.jpg", "List item title", "List item subtitle"),
            ItemDataModel(3,"https://media.istockphoto.com/id/1403500817/photo/the-craggies-in-the-blue-ridge-mountains.jpg?s=612x612&w=0&k=20&c=N-pGA8OClRVDzRfj_9AqANnOaDS3devZWwrQNwZuDSk=", "List item title", "List item subtitle"),
            ItemDataModel(4,"https://t3.ftcdn.net/jpg/06/15/49/68/360_F_615496890_W34yB8VDE6n5pehb5QCt1ek5oEvRo9qA.jpg", "List item title", "List item subtitle"),
            ItemDataModel(5,"https://t4.ftcdn.net/jpg/06/81/00/73/360_F_681007328_NjDzZuCqOjmMTXFZo5rqI2CwEgbk8fGk.jpg", "List item title", "List item subtitle"),
        )
        val dataResponse = DataResponse(images, items)
        return ResultWrapper.Success(dataResponse.toDataResponseDomainModel())
    }
}