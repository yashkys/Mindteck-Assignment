package com.kys.domain.di

import com.kys.domain.usecase.GetInformationUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetInformationUseCase(get()) }
}