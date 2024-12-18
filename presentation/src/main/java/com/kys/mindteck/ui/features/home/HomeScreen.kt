package com.kys.mindteck.ui.features.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.kys.mindteck.model.UiImage
import com.kys.mindteck.model.UiItem
import com.kys.mindteck.model.UiResponse
import com.kys.mindteck.ui.features.components.ShufflingDotsLoader
import com.kys.mindteck.ui.theme.AppTypography
import com.kys.mindteck.ui.theme.Blue
import com.kys.mindteck.ui.theme.GreyLight
import com.kys.mindteck.ui.theme.GreyMedium
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
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
        HomeScreenContent(
            data = response.value,
            paddingValues = innerPadding
        )
    }
}

@Composable
fun HomeScreenContent(data: UiResponse, paddingValues: PaddingValues) {
    var searchQuery by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = paddingValues)
    ) {
        ImagesView(
            images = data.images,
            onClick = {}
        )
        CustomSearchBar(
            query = searchQuery,
            onQueryChange = { searchQuery = it },
        )
        ItemsView(
            images = data.items,
            onClick = {}
        )
    }

}

@Composable
fun ImagesView(images: List<UiImage>, onClick: () -> Unit) {
    val listState = rememberLazyListState()
    val pagerState = rememberPagerState(pageCount = { images.size })
    val currentImageIndex = remember { mutableIntStateOf(pagerState.currentPage) }
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            currentImageIndex.intValue = page
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
//                .height(200.dp)
        ) { page ->
            AsyncImage(
                model = images[page].url,
                contentDescription = "Carousel Image",
                contentScale = ContentScale.Crop,
                onLoading = { Log.i("Image", "Loading image... $page") },
                onError = { Log.i("Image", "Error loading image $page: ${it.result.throwable}") },
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .aspectRatio(1.77f)
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            images.indices.forEach { index ->
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(8.dp)
                        .clip(RoundedCornerShape(50))
                        .background(if (index == currentImageIndex.value) Blue else GreyMedium.copy(alpha = 0.5f))
                )
            }
        }
    }
}

@Composable
fun CustomSearchBar(query: String, onQueryChange: (String) -> Unit) {

}

@Composable
fun ItemsView(images: List<UiItem>, onClick: () -> Unit) {

}
