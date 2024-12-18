package com.kys.mindteck.ui.features.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.kys.mindteck.model.UiResponse
import com.kys.mindteck.ui.features.components.ShufflingDotsLoader
import com.kys.mindteck.ui.theme.AppTypography
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel = koinViewModel<HomeScreenViewModel>()
) {
    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsState()
    val loading = remember {
        mutableStateOf(false)
    }
    val error = remember {
        mutableStateOf<String?>(null)
    }
    val response = remember {
        mutableStateOf<UiResponse>(UiResponse(emptyList(), emptyList()))
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .systemBarsPadding(),
    ) { innerPadding ->

        when (uiState.value) {
            is HomeScreenUIEvents.Loading -> {
                loading.value = true
                error.value = null
            }

            is HomeScreenUIEvents.Success -> {
                loading.value = false
                error.value = null
                val data = (uiState.value as HomeScreenUIEvents.Success)
                response.value = data.response
            }

            is HomeScreenUIEvents.Error -> {
                loading.value = false
                val errorMsg = (uiState.value as HomeScreenUIEvents.Error).message
                error.value = errorMsg
            }
        }
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)){
            if (loading.value) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ShufflingDotsLoader()
                }
            }
            error.value?.let {
                Text(
                    text = it,
                    style = AppTypography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
            }
        }

    }
}