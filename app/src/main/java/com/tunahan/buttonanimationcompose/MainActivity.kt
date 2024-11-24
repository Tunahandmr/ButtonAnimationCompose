package com.tunahan.buttonanimationcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tunahan.buttonanimationcompose.ui.theme.ButtonAnimationComposeTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ButtonAnimationComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            16.dp,
            Alignment.CenterVertically
        ),
    ) {
        val text = remember { mutableStateOf("Click It!") }

        Text(
            text = text.value
        )

        Button(
            onClick = { text.value = "Press Clicked!" },
            modifier = Modifier.pressClickEffect(),
        ) {
            Text(
                text = "Press Button"
            )
        }

        Button(
            onClick = { text.value = "Bounce Clicked!" },
            modifier = Modifier.bounceClickEffect(),
        ) {
            Text(
                text = "Bounce Button"
            )
        }

        Button(
            onClick = { text.value = "Shake Clicked!" },
            modifier = Modifier.shakeClickEffect(),
        ) {
            Text(
                text = "Shake Button"
            )
        }
    }
}


fun Modifier.pressClickEffect() = composed {
    val scope = rememberCoroutineScope()
    var isPressed by remember { mutableStateOf(false) }
    val ty by animateFloatAsState(if (isPressed) 20f else 0f, label = "")

    this
        .graphicsLayer {
            translationY = ty
        }
        .pointerInput(isPressed) {
            scope.launch {
                isPressed = !isPressed
            }
        }
}

fun Modifier.bounceClickEffect() = composed {
    val scope = rememberCoroutineScope()
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (isPressed) 0.70f else 1f, label = "")

    this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .pointerInput(isPressed) {
            scope.launch {
                isPressed= !isPressed
            }
        }
}

fun Modifier.shakeClickEffect() = composed {
    val scope = rememberCoroutineScope()
    var isPressed by remember { mutableStateOf(false) }
    val tx by animateFloatAsState(
        targetValue = if (isPressed) -50f else 0f,
        animationSpec = repeatable(
            iterations = 2,
            animation = tween(durationMillis = 50, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )
    this
        .graphicsLayer {
            translationX = tx
        }
        .pointerInput(isPressed) {
            scope.launch {
                isPressed = !isPressed
            }
        }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ButtonAnimationComposeTheme {
        Greeting("Android")
    }
}