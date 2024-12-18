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
        val images = listOf<ImageDataModel>()
        val items = listOf<ItemDataModel>()
        val dataResponse = DataResponse(images, items)
        return ResultWrapper.Success(listOf(dataResponse.toDataResponseDomainModel()))
    }
}