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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.InsertDriveFile
import androidx.compose.material.icons.outlined.InsertDriveFile
import androidx.compose.material.icons.outlined.Wifi
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
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



//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ReceivingScreen() {
//    val viewModel = ViewModels.receiveScreenViewModel
//    var errorMessage by remember { mutableStateOf<String?>(null) }
//    var isReceiving by remember { mutableStateOf(false) }
//    val receivingFiles = remember { mutableStateListOf<String>() }
//    val receivedFiles = remember { mutableStateListOf<String>() }
//    val totalBytesReceived = remember { mutableStateOf(0L) }
//    val totalTimeElapsed = remember { mutableStateOf(0L) }
//    val speedMBps = remember { mutableStateOf(0.0) }
//
//    Box(modifier = Modifier
//        .fillMaxSize()
//        .background(Color(0xFFF8F9FA))) {
//
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(bottom = 80.dp) // leave space for button
//                .verticalScroll(rememberScrollState())
//        ) {
//            TopAppBar(
//                title = {
//                    Text("Receive Files", fontWeight = FontWeight.Bold, fontSize = 20.sp)
//                },
//                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
//                modifier = Modifier.shadow(4.dp)
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            errorMessage?.let {
//                Text(
//                    "⚠️ $it",
//                    color = Color.Red,
//                    fontWeight = FontWeight.SemiBold,
//                    modifier = Modifier.padding(16.dp)
//                )
//            }
//
//            if (receivingFiles.isNotEmpty()) {
//                SectionHeader("📥 Receiving Files")
//                receivingFiles.forEach { file ->
//                    FileCard(file, "Receiving...", isLoading = true)
//                }
//            }
//
//            if (receivedFiles.isNotEmpty()) {
//                SectionHeader("✅ Received Files")
//                receivedFiles.forEach { file ->
//                    FileCard(file, "Received", isLoading = false)
//                }
//            }
//
//            if (totalBytesReceived.value > 0) {
//                SectionHeader("📊 Transfer Summary")
//                SummaryCard(
//                    sizeMB = "%.2f".format(totalBytesReceived.value / 1024.0 / 1024.0),
//                    elapsedSec = "%.2f".format(totalTimeElapsed.value / 1000.0),
//                    speed = "%.2f".format(speedMBps.value)
//                )
//            }
//        }
//
//        Button(
//            onClick = {
//                isReceiving = true
//                errorMessage = null
//                CoroutineScope(Dispatchers.IO).launch {
//                    try {
//                        Client(
//                            onStartFile = { fileName -> receivingFiles.add(fileName) },
//                            onProgress = { bytesReadTotal, speed ->
//                                totalBytesReceived.value = bytesReadTotal
//                                speedMBps.value = speed
//                            },
//                            onFinishFile = { fileName ->
//                                receivingFiles.remove(fileName)
//                                receivedFiles.add(fileName)
//                            }
//                        )
//                    } catch (e: Exception) {
//                        errorMessage = e.message
//                        Log.e("App", "Client() failed: ${e.message}")
//                    }
//                }
//            },
//            enabled = !isReceiving,
//            modifier = Modifier
//                .align(Alignment.BottomCenter)
//                .padding(16.dp)
//                .fillMaxWidth(),
//            colors = ButtonDefaults.buttonColors(
//                containerColor = Color(0xFF4CAF50),
//                contentColor = Color.White
//            ),
//            shape = RoundedCornerShape(12.dp)
//        ) {
//            Text("Start Receiving", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
//        }
//    }
//}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReceivingScreen() {
    val viewModel = ViewModels.receiveScreenViewModel
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isReceiving by remember { mutableStateOf(false) }
    val receivingFiles = remember { mutableStateMapOf<String, Float>() }
    val receivedFiles = remember { mutableStateListOf<String>() }
    val totalBytesReceived = remember { mutableStateOf(0L) }
    val totalTimeElapsed = remember { mutableStateOf(0L) }
    val speedMBps = remember { mutableStateOf(0.0) }
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F6F8))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)
                .verticalScroll(scrollState)
        ) {
            TopAppBar(
                title = { Text("Receive Files", fontWeight = FontWeight.Bold, fontSize = 20.sp) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White,titleContentColor = Color.Black ),
                modifier = Modifier.shadow(4.dp)
            )

            TransferSummaryCard(
                sizeMB = "%.2f".format(totalBytesReceived.value / 1024.0 / 1024.0),
                elapsedSec = "%.2f".format(totalTimeElapsed.value / 1000.0),
                speed = "%.2f".format(speedMBps.value)
            )

            errorMessage?.let {
                Text(
                    "⚠️ $it",
                    color = Color.Red,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(16.dp)
                )
            }

            if (receivingFiles.isNotEmpty()) {
                SectionHeader("📥 Receiving Files")
                receivingFiles.forEach { (file, progress) ->
                    FileCardWithProgress(fileName = file, progress = progress)
                }

            }

            if (receivedFiles.isNotEmpty()) {
                SectionHeader("✅ Received Files")
                receivedFiles.forEach { file ->
                    FileCard(file, "Received", isLoading = false)
                }
            }
        }

        Button(
            onClick = {
                isReceiving = true
                errorMessage = null
                CoroutineScope(Dispatchers.IO).launch {
                    val startTime = System.currentTimeMillis()
                    try {
                        Client(
                            onStartFile = { fileName ->
                                receivingFiles[fileName] = 0f
                            },

                            onProgress = { bytesReadTotal, speed ->
                                totalBytesReceived.value = bytesReadTotal
                                speedMBps.value = speed

                                val currentFile = receivingFiles.keys.lastOrNull()
                                if (currentFile != null) {
                                    // We assume numberOfFiles also equals total size in bytes? Clarify if not
                                    val fileSize = viewModel.numberOfFiles // Might be wrong — replace with actual fileSize
                                    val progress = if (fileSize > 0) bytesReadTotal.toFloat() / fileSize else 0f
                                    receivingFiles[currentFile] = progress
                                }
                            },
                            onFinishFile = { fileName ->
                                receivingFiles.remove(fileName)
                                receivedFiles.add(fileName)
                            }

                        )
                    } catch (e: Exception) {
                        errorMessage = e.message
                        Log.e("App", "Client() failed: ${e.message}")
                    } finally {
                        totalTimeElapsed.value = System.currentTimeMillis() - startTime
                    }
                }
            },
            enabled = !isReceiving,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4CAF50),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Start Receiving", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}


@Composable
fun FileCardWithProgress(fileName: String, progress: Float) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.InsertDriveFile, contentDescription = null, tint = Color(0xFF3F51B5))
                Spacer(modifier = Modifier.width(12.dp))
                Text(fileName, fontWeight = FontWeight.Medium)
            }
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = progress.coerceIn(0f, 1f),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp),
                color = Color(0xFF4CAF50),
                trackColor = Color.LightGray
            )
        }
    }
}

@Composable
fun TransferSummaryCard(sizeMB: String, elapsedSec: String, speed: String) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
    ) {
        Column(Modifier.padding(16.dp)) {
            Text("📊 Transfer Summary", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Total Size: $sizeMB MB", fontSize = 15.sp)
            Text("Time Elapsed: $elapsedSec sec", fontSize = 15.sp)
            Text("Speed: $speed MBps", fontSize = 15.sp)
        }
    }
}


@Composable
fun FileCard(fileName: String, status: String, isLoading: Boolean) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.InsertDriveFile,
                contentDescription = null,
                tint = Color(0xFF3F51B5),
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(fileName, fontWeight = FontWeight.Medium)
                Text(status, fontSize = 13.sp, color = Color.Gray)
            }
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
            }
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 8.dp)
    )
}

@Composable
fun SummaryCard(sizeMB: String, elapsedSec: String, speed: String) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F3F4))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Total Size: $sizeMB MB", fontSize = 15.sp)
            Text("Time Elapsed: $elapsedSec sec", fontSize = 15.sp)
            Text("Avg Speed: $speed MBps", fontSize = 15.sp)
        }
    }
}



@Composable
fun FileRow(fileName: String, status: String, isLoading: Boolean = false) {
    val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.View)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // File status icon or shimmer animation
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

            // File name and status text
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = fileName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = status,
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }

            // Progress indicator if file is still loading
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp
                )
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewCard() {
//    Cards(name = "Sample_File_Example.pdf")
//}

