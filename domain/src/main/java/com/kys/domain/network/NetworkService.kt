package com.kys.domain.network

import com.kys.domain.model.DataResponseDomainModel
import java.lang.Exception

interface NetworkService {
    suspend fun getDynamicInformation(): ResultWrapper<List<DataResponseDomainModel>>

}
sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T): ResultWrapper<T>()
    data class Failure(val exception: Exception): ResultWrapper<Nothing>()
}