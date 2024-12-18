package com.kys.data.network

import com.kys.data.model.DataResponse
import com.kys.data.model.ImageDataModel
import com.kys.data.model.ItemDataModel
import com.kys.domain.model.DataResponseDomainModel
import com.kys.domain.network.NetworkService
import com.kys.domain.network.ResultWrapper
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.Parameters
import io.ktor.http.contentType
import io.ktor.util.InternalAPI
import java.io.IOException

class NetworkServiceImpl (
    val client: HttpClient
) : NetworkService {
    private val baseUrl = "https://mindteck..com"
    @OptIn(InternalAPI::class)
    suspend inline fun<reified T,R> makeWebRequest(
        url: String,
        method: HttpMethod,
        body: Any? = null,
        headers: Map<String, String> = emptyMap(),
        parameters: Map<String, String> = emptyMap(),
        noinline mapper: ((T) -> R)? = null
    ): ResultWrapper<R> {
        return try {
            val response = client.request(url) {
                this.method = method

                url {
                    this.parameters.appendAll(Parameters.build {
                        parameters.forEach { (key, value) ->
                            append(key, value)
                        }
                    })
                }

                // apply headers
                headers.forEach { (key, value) ->
                    header(key,value)
                }

                if(body!=null) {
                    setBody(body)
                }

                // set content type
                contentType(ContentType.Application.Json)
            }.body<T>()
            val result: R = mapper?.invoke(response) ?: response as R
            ResultWrapper.Success(result)
        }  catch (e: ClientRequestException) {
            ResultWrapper.Failure(e)
        } catch (e: ServerResponseException) {
            ResultWrapper.Failure(e)
        } catch (e: IOException) {
            ResultWrapper.Failure(e)
        } catch (e: Exception) {
            ResultWrapper.Failure(e)
        }

    }

    override suspend fun getDynamicInformation(): ResultWrapper<List<DataResponseDomainModel>> {
        val url = "$baseUrl/list"
//        return makeWebRequest(url = url,
//            method = HttpMethod.Get,
//            mapper = { models: DataResponse ->
//                models.toDataResponseDomainModel()
//            })
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
        return ResultWrapper.Success(listOf(dataResponse.toDataResponseDomainModel()))
    }
}