package com.kys.mindteck.ui.features.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.kys.mindteck.model.UiImage
import com.kys.mindteck.model.UiItem
import com.kys.mindteck.model.UiResponse
import com.kys.mindteck.ui.features.components.ShufflingDotsLoader
import com.kys.mindteck.ui.theme.AppTypography
import com.kys.mindteck.ui.theme.Blue
import com.kys.mindteck.ui.theme.GreenTint
import com.kys.mindteck.ui.theme.GreyLight
import com.kys.mindteck.ui.theme.GreyMedium
import com.kys.mindteck.ui.theme.PoppinsFontFamily
import com.kys.mindteck.ui.theme.WhiteFill
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel = koinViewModel<HomeScreenViewModel>()
) {
    val uiState = viewModel.uiState.collectAsState()
    val loading = remember {
        mutableStateOf(false)
    }
    val error = remember {
        mutableStateOf<String?>(null)
    }
    val response = remember {
        mutableStateOf(UiResponse(emptyList(), emptyList()))
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
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = paddingValues)
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
        ) {
            ImagesView(
                images = data.images,
                onClick = {}
            )
            Spacer(Modifier.height(16.dp))
            CustomSearchBar(
                query = searchQuery,
                onSearchTextChanged = { searchQuery = it },
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(Modifier.height(16.dp))
            ItemsView(
                data = data.items,
                onClick = {}
            )
        }
        FloatingActionButton(
            modifier = Modifier
                .padding(16.dp)
                .size(64.dp)
                .align(Alignment.BottomEnd),
            shape = CircleShape,
            contentColor = WhiteFill,
            containerColor = Blue,
            onClick = {},
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 8.dp,
                pressedElevation = 12.dp
            ),
            content = {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = null,
                )
            }
        )
    }

}

@Composable
fun ImagesView(images: List<UiImage>, onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val pagerState = rememberPagerState(pageCount = { images.size })
    val currentImageIndex = remember { mutableIntStateOf(pagerState.currentPage) }
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            currentImageIndex.intValue = page
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
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
                    //.padding(vertical = 8.dp, horizontal = 16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .aspectRatio(1.77f)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = onClick
                    )
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
                        .background(
                            if (index == currentImageIndex.intValue) Blue else GreyMedium.copy(
                                alpha = 0.5f
                            )
                        )
                )
            }
        }
    }
}

@Composable
fun CustomSearchBar(
    query: String,
    onSearchTextChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val hintColor = GreyMedium //Color.DarkGray
    val backgroundColor = GreyLight //Color.LightGray

    BasicTextField(
        value = query,
        onValueChange = onSearchTextChanged,
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor, RoundedCornerShape(16.dp))
            .padding(8.dp),
        textStyle = TextStyle(
            color = Color.Black,
            fontFamily = PoppinsFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp
        ),
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search",
                    tint = hintColor
                )
                Spacer(modifier = Modifier.width(8.dp))
                if (query.isEmpty()) {
                    Text(
                        text = "Search",
                        color = hintColor
                    )
                }
                innerTextField()
            }
        }
    )
}

@Composable
fun ItemsView(data: List<UiItem>, onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        items(data) { item ->
            Card(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = onClick,
                    ),
                elevation = CardDefaults.cardElevation(2.dp),
                colors = CardDefaults.cardColors(containerColor = Color(GreenTint.value)) // Light green background
            ) {
                Row(
                    modifier = Modifier
                        .padding(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Image on the left
                    AsyncImage(
                        model = item.url,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .size(64.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    // Title and Subtitle
                    Column {
                        Text(
                            text = item.title,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = PoppinsFontFamily,
                            color = Color.Black
                        )
                        Text(
                            text = item.subtitle,
                            fontSize = 14.sp,
                            fontFamily = PoppinsFontFamily,
                            fontWeight = FontWeight.Medium,
                            color = Color.DarkGray
                        )
                    }
                }
            }

        }
    }
}
