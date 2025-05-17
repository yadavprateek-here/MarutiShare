package com.rudra.marutishare.UIScreen

import android.util.Log
import android.view.View
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.InsertDriveFile
import androidx.compose.material.icons.outlined.InsertDriveFile
import androidx.compose.material.icons.outlined.Wifi
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.CarouselDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.rudra.marutishare.Nav
import com.rudra.marutishare.UIScreen.backgroundIndicator
import com.rudra.marutishare.UIScreen.foregroundIndicator
import com.rudra.marutishare.Utils.Client
import com.rudra.marutishare.Utils.StopPeerDiscovery
import com.rudra.marutishare.Utils.connectToPeer
import com.rudra.marutishare.ViewModels
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ReceiveFiles() {
    val viewModel = ViewModels.receiveScreenViewModel
    val isConnected by viewModel.isConnected
    val peer = viewModel.selectedPeer

    // Connect when peer is selected
    LaunchedEffect(peer) {
        connectToPeer()
    }

    // Timeout
    LaunchedEffect(isConnected) {
        if (!isConnected) {
            delay(15_000L)
            if (!viewModel.isConnected.value) {
                viewModel.reset()
                Nav.nav.navigate("receive") // retry screen
            }
        }
    }

    // Navigate after successful connection
    if (isConnected) {
        StopPeerDiscovery()
        Nav.nav.navigate("FileReceivingNetworkPage")
    } else {
        // Show connecting UI
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Outlined.Wifi, contentDescription = null, Modifier.size(64.dp))
                Spacer(modifier = Modifier.height(16.dp))
                Text("Connecting to device...")
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator()
            }
        }
    }
}



