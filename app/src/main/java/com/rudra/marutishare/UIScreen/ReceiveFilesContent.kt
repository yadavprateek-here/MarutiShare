package com.rudra.marutishare.UIScreen

import android.util.Log
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.InsertDriveFile
import androidx.compose.material.icons.outlined.Wifi
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rudra.marutishare.Utils.Client
import com.rudra.marutishare.ViewModels
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//@Composable
//fun ReceiveFilesContent(
//    isVisible: MutableState<Boolean>,
//    onStartReceiving: () -> Unit
//) {
//    var connectionStatus by remember { mutableStateOf("Not connected") }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Top
//    ) {
//
//        if (!isVisible.value) {
//            Text("Receive Files", fontSize = 24.sp, fontWeight = FontWeight.Bold)
//            Spacer(modifier = Modifier.height(24.dp))
//
//            Icon(
//                imageVector = Icons.Outlined.Wifi,
//                contentDescription = "Wi-Fi Direct",
//                modifier = Modifier.size(100.dp),
//                tint = Color.Gray
//            )
//            Spacer(modifier = Modifier.height(12.dp))
//            Text(
//                text = "Not visible",
//                fontSize = 16.sp
//            )
//            Spacer(modifier = Modifier.height(24.dp))
//
//            Button(
//                onClick = {
//                    isVisible.value = true
//                    onStartReceiving()
//                    connectionStatus = "Waiting for sender..."
//                },
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text("Stop Receiving")
//            }
//
//            Spacer(modifier = Modifier.height(36.dp))
//            Text("Connection Status:", fontSize = 18.sp, fontWeight = FontWeight.Medium)
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(
//                text = connectionStatus,
//                fontSize = 16.sp,
//                color = if (connectionStatus.contains("Connected")) Color.Green else Color.Gray
//            )
//        } else {
//            Column(horizontalAlignment = Alignment.CenterHorizontally) {
//
//                CustomComponent()
//
//                LazyColumn {
//                    items(ViewModels.receiveScreenViewModel.numberOfFiles) { index ->
//                        Cards(index.toString())
//                    }
//                }
//            }
//        }
//    }
//}

//@Composable
//fun ReceivingScreen() {
//    val viewModel = ViewModels.receiveScreenViewModel
//    val context = LocalContext.current
//    var errorMessage by remember { mutableStateOf<String?>(null) }
//    var started by remember { mutableStateOf(false) }
//
//    // Start Client() only once
//    LaunchedEffect(Unit) {
//        if (!started) {
//            started = true
//            try {
//                withContext(Dispatchers.IO) {
//                    Client() // 🔁 Your file receiving logic
//                }
//            } catch (e: Exception) {
//                errorMessage = e.message
//                Log.e("App", "Client() failed: ${e.message}")
//            }
//        }
//    }
//
//
//    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
//        Text("Receiving Files", fontSize = 22.sp, fontWeight = FontWeight.Bold)
//        Spacer(modifier = Modifier.height(12.dp))
//
//        if (errorMessage != null) {
//            Text("Error: $errorMessage", color = Color.Red)
//        }
//
//        CustomComponent( modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp))
//
//        LazyColumn {
//            items(viewModel.numberOfFiles) { index ->
//                Cards(index.toString())
//            }
//        }
//    }
//}

//
//@Composable
//fun ReceivingScreen() {
//    val viewModel = ViewModels.receiveScreenViewModel
//    val context = LocalContext.current
//    var errorMessage by remember { mutableStateOf<String?>(null) }
//    var isReceiving by remember { mutableStateOf(false) }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        Text("Receiving Files", fontSize = 22.sp, fontWeight = FontWeight.Bold)
//        Spacer(modifier = Modifier.height(12.dp))
//
//        // Show Error if any
//        if (errorMessage != null) {
//            Text("Error: $errorMessage", color = Color.Red)
//            Spacer(modifier = Modifier.height(8.dp))
//        }
//
//        // Start Receiving Button
//        Button(
//            onClick = {
//                isReceiving = true
//                errorMessage = null
//
//                // Launch receiving logic in background
//                CoroutineScope(Dispatchers.IO).launch {
//                    try {
//                        Client()
//                    } catch (e: Exception) {
//                        errorMessage = e.message
//                        Log.e("App", "Client() failed: ${e.message}")
//                    }
//                }
//            },
//            enabled = !isReceiving,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("Start Receiving")
//        }
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        // Show your CustomComponent
//        CustomComponent(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 16.dp)
//        )
//
//        // Show received files
//        LazyColumn {
//            items(viewModel.numberOfFiles) { index ->
//                Cards(index.toString())
//            }
//        }
//    }
//}


//@Composable
//fun ReceivingScreen() {
//    val viewModel = ViewModels.receiveScreenViewModel
//    var errorMessage by remember { mutableStateOf<String?>(null) }
//    var isReceiving by remember { mutableStateOf(false) }
//    val receivingFiles = remember { mutableStateListOf<String>() }
//    val receivedFiles = remember { mutableStateListOf<String>() }
//
//    var currentSpeed by remember { mutableStateOf("0 KB/s") }
//
//    Box(modifier = Modifier.fillMaxSize()) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp)
//        ) {
//            Text("Receive Files", fontSize = 24.sp, fontWeight = FontWeight.Bold)
//            Spacer(modifier = Modifier.height(16.dp))
//
//            if (errorMessage != null) {
//                Text("Error: $errorMessage", color = Color.Red)
//                Spacer(modifier = Modifier.height(8.dp))
//            }
//
//            // Speed Display
//            if (isReceiving) {
//                Text("Speed: $currentSpeed", color = Color.DarkGray, fontSize = 14.sp)
//                Spacer(modifier = Modifier.height(8.dp))
//            }
//
//            // Currently Receiving Files
//            if (receivingFiles.isNotEmpty()) {
//                Text("📥 Receiving Now", fontSize = 18.sp, fontWeight = FontWeight.Medium)
//                Spacer(modifier = Modifier.height(8.dp))
//
//                LazyColumn {
//                    items(receivingFiles) { file ->
//                        FileRow(fileName = file, status = "Receiving", isLoading = true)
//                    }
//                }
//                Spacer(modifier = Modifier.height(8.dp))
//            }
//
//            // Completed Files
//            if (receivedFiles.isNotEmpty()) {
//                Text("✅ Received Files", fontSize = 18.sp, fontWeight = FontWeight.Medium)
//                Spacer(modifier = Modifier.height(8.dp))
//
//                LazyColumn {
//                    items(receivedFiles) { file ->
//                        FileRow(fileName = file, status = "Received", isLoading = false)
//                    }
//                }
//            }
//        }
//
//        // Button pinned to bottom
//        Button(
//            onClick = {
//                isReceiving = true
//                errorMessage = null
//
//                CoroutineScope(Dispatchers.IO).launch {
//                    try {
//                        Client(
//                            onStartFile = { fileName ->
//                                receivingFiles.add(fileName)
//                            },
//                            onProgress = { bytesRead, timeElapsed ->
//                                val speedInKB = (bytesRead / (timeElapsed + 1).toDouble()) * 1000 / 1024
//                                currentSpeed = "%.2f KB/s".format(speedInKB)
//                            },
//                            onFinishFile = { fileName ->
//                                receivingFiles.remove(fileName)
//                                receivedFiles.add(fileName)
//                            }
//                        )
//                    } catch (e: Exception) {
//                        errorMessage = e.message
//                        Log.e("App", "Client() failed: ${e.message}")
//                    } finally {
//                        isReceiving = false
//                        currentSpeed = "0 KB/s"
//                    }
//                }
//            },
//            enabled = !isReceiving,
//            modifier = Modifier
//                .align(Alignment.BottomCenter)
//                .fillMaxWidth()
//                .padding(16.dp)
//        ) {
//            Text(if (isReceiving) "Receiving..." else "Start Receiving")
//        }
//
//    }
//}

@Composable
fun ReceivingScreen() {
    val viewModel = ViewModels.receiveScreenViewModel
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isReceiving by remember { mutableStateOf(false) }
    val receivingFiles = remember { mutableStateListOf<String>() } // files in progress
    val receivedFiles = remember { mutableStateListOf<String>() }  // files done
    val totalBytesReceived = remember { mutableStateOf(0L) }
    val totalTimeElapsed = remember { mutableStateOf(0L) }
    val speedMBps = remember { mutableStateOf(0.0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Receive Files", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        if (errorMessage != null) {
            Text("Error: $errorMessage", color = Color.Red)
            Spacer(modifier = Modifier.height(8.dp))
        }

        Button(
            onClick = {
                isReceiving = true
                errorMessage = null

                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        Client(
                            onStartFile = { fileName ->
                                receivingFiles.add(fileName)
                            },
                            onProgress = { bytesReadTotal, speed ->
                                totalBytesReceived.value = bytesReadTotal
                                speedMBps.value = speed
                            },
                            onFinishFile = { fileName ->
                                receivingFiles.remove(fileName)
                                receivedFiles.add(fileName)
                            }
                        )
                    } catch (e: Exception) {
                        errorMessage = e.message
                        Log.e("App", "Client() failed: ${e.message}")
                    }
                }
            },
            enabled = !isReceiving,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Start Receiving")
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (receivingFiles.isNotEmpty()) {
            Text("📥 Receiving Now", fontSize = 18.sp, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(8.dp))

            Column {
                receivingFiles.forEach { file: String ->
                    FileRow(fileName = file, status = "Receiving")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Speed: ${"%.2f".format(speedMBps.value)} MBps")
        }

        if (receivedFiles.isNotEmpty()) {
            Text("✅ Received Files", fontSize = 18.sp, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn {
                items(receivedFiles) { file ->
                    FileRow(fileName = file, status = "Received")
                }
            }
        }
    }
}



@Composable
fun FileRow(fileName: String, status: String, isLoading: Boolean = false) {
    val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.View)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .shimmer(shimmerInstance)
                        .background(Color.LightGray, shape = CircleShape)
                )
            } else {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Color(0xFF4CAF50)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = fileName,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(12.dp))

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text = status,
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }
        }
    }
}





@Composable
fun Cards(name: String = "No_Name") {
    Card(
        elevation = CardDefaults.cardElevation(6.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9)),
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.InsertDriveFile,
                contentDescription = "File Icon",
                tint = Color(0xFF3F51B5),
                modifier = Modifier.size(28.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = name,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCard() {
    Cards(name = "Sample_File_Example.pdf")
}

