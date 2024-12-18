package com.kys.mindteck.di

import com.kys.mindteck.ui.features.home.HomeScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        HomeScreenViewModel(get())
    }
}