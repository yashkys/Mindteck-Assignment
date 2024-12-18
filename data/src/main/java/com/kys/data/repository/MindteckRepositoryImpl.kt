package com.kys.data.repository

import com.kys.domain.model.DataResponseDomainModel
import com.kys.domain.network.NetworkService
import com.kys.domain.network.ResultWrapper
import com.kys.domain.repository.MindteckRepository

class MindteckRepositoryImpl(
    private val networkService: NetworkService
): MindteckRepository {
    override suspend fun getDynamicInformation(): ResultWrapper<List<DataResponseDomainModel>> {
        return networkService.getDynamicInformation()
    }
}