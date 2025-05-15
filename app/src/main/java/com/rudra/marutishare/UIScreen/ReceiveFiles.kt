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
import com.rudra.marutishare.Utils.connectToPeer
import com.rudra.marutishare.ViewModels
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//@Preview
//@Composable
//fun ReceiveFiles(){
//    var isVisible by remember { mutableStateOf(false) }
//    var connectionStatus by remember { mutableStateOf("Not connected") }
//
//    LaunchedEffect(ViewModels.receiveScreenViewModel.selectedPeer) {
//        connectToPeer()
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Top
//    ) {
//
//        if(!isVisible) {
//
//            Text(
//                text = "Receive Files",
//                fontSize = 24.sp,
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier.padding(top = 16.dp)
//            )
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            Icon(
//                imageVector = Icons.Outlined.Wifi,
//                contentDescription = "Wi-Fi Direct",
//                modifier = Modifier.size(100.dp),
//                tint = if (isVisible) Color.Green else Color.Gray
//            )
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            Text(
//                text = if (isVisible) "Visible to nearby devices" else "Not visible",
//                fontSize = 16.sp
//            )
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            Button(
//                onClick = {
//                    CoroutineScope(Dispatchers.IO).launch {
//                        Client()
//                    }
//                    isVisible = !isVisible
//                    connectionStatus = if (isVisible) "Waiting for sender..." else "Not connected"
//                    // Trigger discoverability (group owner setup) in logic layer
//                },
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text(if (isVisible) "Stop Receiving" else "Start Receiving")
//            }
//
//            Spacer(modifier = Modifier.height(36.dp))
//
//            Text(
//                text = "Connection Status:",
//                fontSize = 18.sp,
//                fontWeight = FontWeight.Medium
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            Text(
//                text = connectionStatus,
//                fontSize = 16.sp,
//                color = if (connectionStatus.contains("Connected")) Color.Green else Color.Gray
//            )
//        }else{
//
//
//            Box(
//                modifier = Modifier.fillMaxSize(),
//                contentAlignment = Alignment.TopCenter,
//
//                ) {
//                Column(verticalArrangement = Arrangement.Top,
//                    horizontalAlignment = Alignment.CenterHorizontally) {
//
//                    CustomComponent()
//
//                    LazyColumn() {
//                        items(ViewModels.receiveScreenViewModel.numberOfFiles) {
//                            Log.d("App", "items = $it")
//                            Cards(it.toString())
//                        }
//                    }
//
//                    }
//                }
//        }
//
//        //
//    }
//}


//@Composable
//fun ReceiveFiles() {
//    val viewModel = ViewModels.receiveScreenViewModel
//    var isVisible by remember { mutableStateOf(false) }
//
//    val isConnected by viewModel.isConnected
//    val peer = viewModel.selectedPeer
//
//    // 🔁 Try connecting to peer
//    LaunchedEffect(peer) {
//        Log.d("App","LaunchEffect to Connect Peers")
//        connectToPeer()
//    }
//
//    // ⏱ Timeout handling
//    LaunchedEffect(isConnected) {
//        if (!isConnected) {
//            delay(15_000L) // 15 seconds timeout
//            if (!viewModel.isConnected.value) {
//                viewModel.reset()
//               Nav.nav.navigate("receive")
//            }
//        }
//    }
//
//    if (!isConnected) {
//        // ⏳ Show connecting UI
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color(0xFFF0F0F0)),
//            contentAlignment = Alignment.Center
//        ) {
//            Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                Icon(
//                    imageVector = Icons.Outlined.Wifi,
//                    contentDescription = "Connecting",
//                    modifier = Modifier.size(64.dp),
//                    tint = Color.Gray
//                )
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                Text(
//                    text = "Connecting to device...",
//                    fontSize = 18.sp,
//                    fontWeight = FontWeight.Medium,
//                    color = Color.DarkGray
//                )
//
//                Spacer(modifier = Modifier.height(12.dp))
//
//                LinearProgressIndicator(
//                    modifier = Modifier
//                        .fillMaxWidth(0.6f)
//                        .height(6.dp),
//                    color = Color.Blue,
//                    trackColor = Color.LightGray
//                )
//            }
//        }
//    } else {
//        // ✅ Connected UI
//        ReceiveFilesContent(
//            isVisible = isVisible,
//            onStartReceiving = {
//                CoroutineScope(Dispatchers.IO).launch {
//                    Client()
//                }
//                isVisible = true
//            }
//        )
//    }
//}


//@Composable
//fun ReceiveFiles() {
//    val viewModel = ViewModels.receiveScreenViewModel
//    var isVisible by remember { mutableStateOf(false) }
//    val isConnected by viewModel.isConnected
//    val peer = viewModel.selectedPeer
//
//    // 🔁 Start connection attempt when peer is selected
//    LaunchedEffect(peer) {
//        Log.d("App", "Launching connectToPeer() for: $peer")
//        connectToPeer()
//    }
//
//    // ⏱ Timeout after 15 seconds if not connected
//    LaunchedEffect(isConnected) {
//        if (!isConnected) {
//            delay(15_000L) // 15 seconds
//            if (!viewModel.isConnected.value) {
//                viewModel.reset()
//                Nav.nav.navigate("receive") // Redirect to retry screen
//            }
//        }
//    }
//
//    // 🟢 Automatically start receiving when connected
//    LaunchedEffect(isConnected) {
//        if (isConnected && !isVisible) {
//            Log.d("App", "Connected! Starting Client()")
//            isVisible = true
//            try {
//                Client()
//            } catch (e: Exception) {
//                Log.e("App", "Error in Client(): ${e.message}")
//            }
//        }
//    }
//
//    // 👇 UI Logic
//    if (!isConnected) {
//        // ⏳ Show "connecting" UI
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color(0xFFF0F0F0)),
//            contentAlignment = Alignment.Center
//        ) {
//            Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                Icon(
//                    imageVector = Icons.Outlined.Wifi,
//                    contentDescription = "Connecting",
//                    modifier = Modifier.size(64.dp),
//                    tint = Color.Gray
//                )
//                Spacer(modifier = Modifier.height(16.dp))
//                Text(
//                    text = "Connecting to device...",
//                    fontSize = 18.sp,
//                    fontWeight = FontWeight.Medium,
//                    color = Color.DarkGray
//                )
//                Spacer(modifier = Modifier.height(12.dp))
//                LinearProgressIndicator(
//                    modifier = Modifier
//                        .fillMaxWidth(0.6f)
//                        .height(6.dp),
//                    color = Color.Blue,
//                    trackColor = Color.LightGray
//                )
//            }
//        }
//    } else if (isVisible) {
//        // ✅ Connected and ready to receive files
//        ReceiveFilesContent(
//            isVisible = true,
//            onStartReceiving = {} // Already started automatically
//        )
//    }
//}

//@Composable
//fun ReceiveFiles() {
//    val viewModel = ViewModels.receiveScreenViewModel
//    var isVisible = remember { mutableStateOf(false) }
//    val isConnected by viewModel.isConnected
//    val peer = viewModel.selectedPeer
//
//    // 🔁 Start connection attempt when peer is selected
//    LaunchedEffect(peer) {
//        Log.d("App", "Launching connectToPeer() for: $peer")
//        connectToPeer()
//    }
//
//    // ⏱ Timeout handling
//    LaunchedEffect(isConnected) {
//        if (!isConnected) {
//            delay(15_000L)
//            if (!viewModel.isConnected.value) {
//                viewModel.reset()
//                Nav.nav.navigate("receive")
//            }
//        }
//    }
//
//    // UI logic
//    if (!isConnected) {
//        // ⏳ Show "connecting" UI
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color(0xFFF0F0F0)),
//            contentAlignment = Alignment.Center
//        ) {
//            Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                Icon(
//                    imageVector = Icons.Outlined.Wifi,
//                    contentDescription = "Connecting",
//                    modifier = Modifier.size(64.dp),
//                    tint = Color.Gray
//                )
//                Spacer(modifier = Modifier.height(16.dp))
//                Text(
//                    text = "Connecting to device...",
//                    fontSize = 18.sp,
//                    fontWeight = FontWeight.Medium,
//                    color = Color.DarkGray
//                )
//                Spacer(modifier = Modifier.height(12.dp))
//                LinearProgressIndicator(
//                    modifier = Modifier
//                        .fillMaxWidth(0.6f)
//                        .height(6.dp),
//                    color = Color.Blue,
//                    trackColor = Color.LightGray
//                )
//            }
//        }
//    } else {
//        ReceiveFilesContent(
//            isVisible = isVisible,
//            onStartReceiving = {
//                CoroutineScope(Dispatchers.IO).launch {
//                    try {
//                        Client()
//                    } catch (e: Exception) {
//                        Log.e("App", "Error in Client(): ${e.message}")
//                    }
//                }
//            }
//        )
//    }
//}


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



