package com.kys.mindteck.ui.features.home

import com.kys.mindteck.model.UiResponse

sealed class HomeScreenUIEvents {
    data object Loading: HomeScreenUIEvents()
    data class Success(
        val response: UiResponse
    ): HomeScreenUIEvents()
    data class Error(val message: String): HomeScreenUIEvents()
}
