package hu.ait.stayhard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import dagger.hilt.android.AndroidEntryPoint
import hu.ait.stayhard.ui.screen.HabitListScreen
import hu.ait.stayhard.ui.screen.SplashScreen
import hu.ait.stayhard.ui.theme.StayHardTheme
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StayHardTheme {
                AppNavigation {
                    setContent {
                        HabitListScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun AppNavigation(OnSplashFinished: () -> Unit) {
    var showSplashScreen by remember { mutableStateOf(true) }

    LaunchedEffect(showSplashScreen) {
        delay(2000)
        showSplashScreen = false
        OnSplashFinished()
    }
    if (showSplashScreen) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            AppLogo(modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
fun AppLogo(modifier: Modifier = Modifier) {
    // Replace R.drawable.your_icon with the actual resource ID of your icon
    val appIconPainter = painterResource(id = R.drawable.dall_e_2023_12_10_23_42_27___a_motivational_icon_for_habit_tracking__titled__stay_hard___the_design_incorporates_a_strong__bold_font_for_the_title__stay_hard__integrated_into_the_)

    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),


        contentAlignment = Alignment.Center,

    ) {
        Image(
            painter = appIconPainter,
            contentDescription = null,
            contentScale = ContentScale.None
        )
    }
}

