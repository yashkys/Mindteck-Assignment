package com.kys.domain.repository

import com.kys.domain.model.DataResponseDomainModel
import com.kys.domain.network.ResultWrapper

interface MindteckRepository {
    suspend fun getDynamicInformation(): ResultWrapper<List<DataResponseDomainModel>>
}
