package com.kys.domain.usecase

import com.kys.domain.repository.MindteckRepository

class GetInformationUseCase(
    private val repository: MindteckRepository
) {
    suspend fun execute() = repository.getDynamicInformation()
}