package com.kys.mindteck.di

import org.koin.dsl.module

val presentationModule = module {
    includes(viewModelModule)
}