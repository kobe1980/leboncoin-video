package com.leboncoin.video.ui.video

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

@Composable
fun VideoPlayer(
    videoUrl: String,
    modifier: Modifier = Modifier,
    autoPlay: Boolean = false,
    showControls: Boolean = true,
    onPlayerReady: (ExoPlayer) -> Unit = {}
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    // Création du player ExoPlayer avec remember pour éviter les recreations
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(videoUrl))
            prepare()
            playWhenReady = autoPlay
            repeatMode = Player.REPEAT_MODE_ONE // Pour les vidéos shorts
        }
    }

    // Callback pour notifier que le player est prêt
    LaunchedEffect(exoPlayer) {
        onPlayerReady(exoPlayer)
    }

    // Gestion du cycle de vie
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> {
                    exoPlayer.pause()
                }
                Lifecycle.Event.ON_RESUME -> {
                    if (autoPlay) {
                        exoPlayer.play()
                    }
                }
                Lifecycle.Event.ON_DESTROY -> {
                    exoPlayer.release()
                }
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            exoPlayer.release()
        }
    }

    // PlayerView intégré dans Compose
    Box(
        modifier = modifier
            .background(Color.Black)
    ) {
        AndroidView(
            factory = { context ->
                PlayerView(context).apply {
                    player = exoPlayer
                    useController = showControls
                    // Configuration pour les vidéos shorts
                    controllerAutoShow = false
                    controllerHideOnTouch = true
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun CompactVideoPlayer(
    videoUrl: String,
    modifier: Modifier = Modifier,
    onVideoClick: () -> Unit = {}
) {
    // Version compacte pour les cartes de listing
    VideoPlayer(
        videoUrl = videoUrl,
        modifier = modifier
            .aspectRatio(16f/9f)
            .background(
                MaterialTheme.colorScheme.surfaceVariant,
                MaterialTheme.shapes.medium
            ),
        autoPlay = false,
        showControls = false
    )
}