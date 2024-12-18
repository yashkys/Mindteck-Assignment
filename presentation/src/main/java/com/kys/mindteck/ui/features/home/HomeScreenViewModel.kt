package com.kys.mindteck.ui.features.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kys.domain.network.ResultWrapper
import com.kys.domain.usecase.GetInformationUseCase
import com.kys.mindteck.model.UiResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val getInformationUseCase: GetInformationUseCase
): ViewModel() {
    private val _uiState =
        MutableStateFlow<HomeScreenUIEvents>(HomeScreenUIEvents.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            _uiState.value = HomeScreenUIEvents.Loading
            try {
                getInformationUseCase.execute().let { result ->
                    when(result) {
                        is ResultWrapper.Success -> {
                            val response = UiResponse.toUiResponse(result.value)
                            Log.i("App Success", "Successfully fetched data from network")
                            _uiState.value = HomeScreenUIEvents.Success(response)
                        }
                        is ResultWrapper.Failure -> {
                            Log.e("App Exception", result.exception.message?:"Unknown error while fetching data from network")
                            _uiState.value = HomeScreenUIEvents.Error(result.exception.message?:"Something went Wrong")
                        }
                    }

                }
            } catch (exception: Exception) {
                Log.e("App Exception", exception.message?:"Unknown error while fetching data from network")
                _uiState.value = HomeScreenUIEvents.Error(exception.message?:"Something went Wrong")
            }
        }
    }

}