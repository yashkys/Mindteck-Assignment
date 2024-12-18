package com.kys.data.di

import com.kys.data.repository.MindteckRepositoryImpl
import com.kys.domain.repository.MindteckRepository
import org.koin.dsl.module

val  repositoryModule = module{
    single <MindteckRepository> {
        MindteckRepositoryImpl(get())
    }
}