package hu.ait.stayhard.ui.screen

import android.net.Uri
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import hu.ait.stayhard.R
import hu.ait.stayhard.R.string
import hu.ait.stayhard.ui.data.Category
import hu.ait.stayhard.ui.data.HabitItem
import hu.ait.stayhard.ui.data.Status
import kotlinx.coroutines.launch
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitListScreen(
    modifier: Modifier = Modifier,
    habitViewModel: HabitViewModel = hiltViewModel(),
) {
    val coroutineScope = rememberCoroutineScope()

    var showAddHabitDialog by rememberSaveable {
        mutableStateOf(false)
    }
    var habitToEdit: HabitItem? by rememberSaveable {
        mutableStateOf(null)
    }
    var selectedCategory by rememberSaveable {
        mutableStateOf(Category.GYM)
    }
    var isPlay by rememberSaveable {
        mutableStateOf(false)
    }
    var showFilter by rememberSaveable { mutableStateOf(false) }

    var options = listOf("Gym", "Substance Abuse", "Money")

    Column {

        TopAppBar(
            title = {
                Text(
                    "StayHard",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(com.google.android.exoplayer2.ui.R.font.roboto_medium_numbers)),
                    color = Color.White, // Elegant text color
                    modifier = Modifier.padding(top = 20.dp) // Added top padding
                )
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = Color(android.graphics.Color.parseColor("#235789")), // A more subtle and elegant color
            ),
            modifier = Modifier
                .height(80.dp)
                .padding(), // Added padding for a cleaner look
            actions = {
                IconButton(
                    onClick = { /* Implement filter/category functionality */ },
                    modifier = Modifier.padding(top = 10.dp) // Added top padding to the IconButton
                ) {
                    Icon(painter = painterResource(id = R.drawable.icons8_category_64), contentDescription = "filter", tint = Color.White)
                }
                IconButton(
                    onClick = { showFilter=true},
                    modifier = Modifier.padding(top = 10.dp) // Added top padding to the IconButton
                ) {
                    Icon(painter = painterResource(id = R.drawable.icons8_refresh_64), contentDescription = "refresh", tint = Color.White)
                }
                IconButton(
                    onClick = { /* Implement refresh functionality */ },
                    modifier = Modifier.padding(top = 10.dp) // Added top padding to the IconButton
                ) {
                    Icon(painter = painterResource(id = R.drawable.not_accomplished_icon), contentDescription = "summary", tint = Color.White)
                }
                // Your CategoryDropdown code with potentially updated styling
            }
            // Removed the elevation parameter
        )

        if(showFilter){
            showFilter(showDialog = showFilter, options = options, onDismiss = {showFilter = false}) { selectedOption ->
                // Handle the selected option
            }

        }



        Column() {
            ScrollVideos()
            if (showAddHabitDialog) {
            }
            val sortedHabitList by habitViewModel.getHabitListSortedByCategory(selectedCategory)
                .collectAsState(emptyList())

            if (sortedHabitList.isNotEmpty()) {
                LazyColumn(modifier = Modifier.fillMaxHeight()) {
                    items(sortedHabitList) { habitItem ->
                        HabitCard(
                            habitItem = habitItem,
                            onRemoveItem = { habitViewModel.removeHabitItem(habitItem) },
                            onHabitCheckChange = { isChecked ->
                                val newStatus = if (isChecked) Status.ACCOMPLISHED else Status.NOT_ACCOMPLISHED
                                habitViewModel.changeHabitState(habitItem, newStatus)
                            },
                            onEditItem = { editedHabitItem ->
                                habitToEdit = editedHabitItem
                                showAddHabitDialog = true
                            },
                            onDeleteItem = {
                                habitViewModel.removeHabitItem(habitItem)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun showFilter(
    showDialog: Boolean,
    options: List<String>,
    onDismiss: () -> Unit = {},
    onOptionSelected: (String) -> Unit
) {
    if (showDialog) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Card(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF5F5F5) // A subtle background color
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        "Select an Option",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Divider()
                    options.forEach { option ->
                        Button(
                            onClick = {
                                onOptionSelected(option)
                                onDismiss()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            Text(option)
                        }
                    }
                }
            }
        }
    }
}



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScrollVideos() {
    val videoMap = VideoMap()
    val videos = videoMap.mapVideos()
    var currentlyPlaying by remember { mutableStateOf<String?>(null) }
    val pagerState = rememberPagerState()

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.abstract_surface_textures_white_concrete_stone_wall), // Replace with your drawable resource ID
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop // Adjust as needed
        )

        HorizontalPager(
            pageCount = videos.size,
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            VideoCard(
                videoUrl = videos[page].url,
                imageUrl = videos[page].imageUrl,
                isPlaying = currentlyPlaying == videos[page].url,
                onCardClick = {
                    if (currentlyPlaying != videos[page].url) {
                        currentlyPlaying = videos[page].url
                    } else {
                        currentlyPlaying = null // Stop the video if the same card is clicked again
                    }
                }
            )
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoCard(
    videoUrl: String,
    imageUrl: String,
    isPlaying: Boolean,
    onCardClick: () -> Unit
) {
    var isViewed by rememberSaveable { mutableStateOf(false) }

    ElevatedCard(
        onClick = {
            isViewed = true
            onCardClick()
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(900.dp)
            .padding(16.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.dall_e_2023_12_11_01_41_17___a_background_image_designed_for_a_video_loading_placeholder__featuring_a_smooth_and_solid_gradient__the_gradient_should_transition_seamlessly_from_one), // Replace with your drawable resource ID
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop // Adjust as needed
            )

            if (isViewed) {
                VideoPlayer(
                    videoUri = Uri.parse(videoUrl),
                    isPlaying = isPlaying,
                    onTogglePlay = { onCardClick() }
                )
            } else {
                ImageDisp(url = imageUrl)
            }
        }
    }
}


@Composable
fun VideoPlayer(videoUri: Uri, isPlaying: Boolean, onTogglePlay: () -> Unit) {
    val context = LocalContext.current
    val exoPlayer = remember {
        SimpleExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(videoUri))
            prepare()
            playWhenReady = isPlaying
        }
    }

    DisposableEffect(Unit) {
        onDispose { exoPlayer.release() }
    }

    AndroidView(
        factory = { context ->
            PlayerView(context).apply {
                player = exoPlayer
                useController = false // Disable the built-in playback controls
                setOnClickListener { onTogglePlay() } // Toggle play/pause on click
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun ImageDisp(url: String) {
    val painter = rememberImagePainter(
        data = url,
        builder = {
            crossfade(true)
            // You can add error handling, placeholders, etc. here
        }
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painter,
            contentDescription = null, // provide descriptive text for screen readers
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop // Crop and scale the image to fill the available space
        )
    }
}


@Composable
fun HabitCard(
    habitItem: HabitItem,
    onHabitCheckChange: (Boolean) -> Unit = {},
    onRemoveItem: () -> Unit = {},
    onEditItem: (HabitItem) -> Unit = {},
    onDeleteItem: () -> Unit = {},
    onMoreInfo: () -> Unit = {}
) {
    var expanded by rememberSaveable {
        mutableStateOf(false)
    }

    val cardColor = if (habitItem.status == Status.ACCOMPLISHED) Color.Green else MaterialTheme.colorScheme.surfaceVariant
    val borderColor = if (habitItem.status == Status.ACCOMPLISHED) Color.Green else MaterialTheme.colorScheme.primary

    Card(
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier
            .padding(5.dp)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .animateContentSize(
                    animationSpec = spring()
                )
        ) {
            Row(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = habitItem.category.getIcon()),
                    contentDescription = stringResource(string.category),
                    modifier = Modifier
                        .size(50.dp)
                        .padding(end = 10.dp)
                )

                Column(
                    modifier = Modifier.fillMaxWidth(0.2f)
                ) {
                    Text(habitItem.name)
                    Text(
                        text = stringResource(
                            string.dollar,
                            habitItem.estimatedPrice,
                            habitItem.description
                        ),
                        style = TextStyle(
                            fontSize = 10.sp,
                        )
                    )
                }

                Spacer(modifier = Modifier.fillMaxSize(0.35f))
                Checkbox(
                    checked = habitItem.status == Status.ACCOMPLISHED,
                    onCheckedChange = { isChecked ->
                        onHabitCheckChange(isChecked)
                    }
                )
                Spacer(modifier = Modifier.width(10.dp))
                IconButton(onClick = { onDeleteItem() }) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = stringResource(string.delete),
                        tint = Color.Red
                    )
                }
                IconButton(onClick = { onEditItem(habitItem) }) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = stringResource(string.edit),
                        tint = Color.Blue
                    )
                }
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded)
                            Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                        contentDescription = if (expanded) {
                            stringResource(string.less)
                        } else {
                            stringResource(string.more)
                        }
                    )
                }
            }

            Divider()

            if (expanded) {
                Text(
                    text = stringResource(string.estimated_price, habitItem.estimatedPrice),
                    style = TextStyle(
                        fontSize = 12.sp,
                    )
                )
                Text(
                    text = stringResource(string.description, habitItem.description),
                    style = TextStyle(
                        fontSize = 12.sp,
                    )
                )
            }
        }
    }
}

@Composable
fun SpinnerSample(
    list: List<String>,
    preselected: String,
    onSelectionChanged: (myData: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selected by remember { mutableStateOf(preselected) }
    var expanded by remember { mutableStateOf(false) }

    OutlinedCard(
        modifier = modifier.clickable {
            expanded = !expanded
        }
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top,
        ) {
            Text(
                text = selected,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
            Icon(Icons.Outlined.ArrowDropDown, null, modifier = Modifier.padding(8.dp))
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                list.forEach { listEntry ->
                    DropdownMenuItem(
                        onClick = {
                            selected = listEntry
                            expanded = false
                            onSelectionChanged(selected)
                        },
                        text = {
                            Text(
                                text = listEntry,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.Start)
                            )
                        },
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryDropdown(
    selectedCategory: Category,
    onCategorySelected: (Category) -> Unit
) {
    val categoryList = Category.values().map { it.name }
    SpinnerSample(
        list = categoryList,
        preselected = selectedCategory.name,
        onSelectionChanged = { selectedCategoryName ->
            val selectedCategory = Category.valueOf(selectedCategoryName)
            onCategorySelected(selectedCategory)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
    )
}