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
import androidx.compose.material.icons.filled.FileDownload
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
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.draw.clip
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



@Composable
fun ReceivingScreen() {
    val viewModel = ViewModels.receiveScreenViewModel
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isReceiving by remember { mutableStateOf(false) }

    val receivingFiles = remember { mutableStateMapOf<String, Float>() } // file -> progress 0f..1f
    val receivedFiles = remember { mutableStateListOf<String>() }

    val totalBytesReceived = remember { mutableStateOf(0L) }
    val totalTimeElapsed = remember { mutableStateOf(0L) }
    val speedMBps = remember { mutableStateOf(0.0) }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 70.dp)
    ) {
        SectionHeader("📊 Transfer Summary")
        SummaryCard(
            sizeMB = String.format("%.2f", totalBytesReceived.value / (1024.0 * 1024)),
            elapsedSec = (totalTimeElapsed.value / 1000).toString(),
            speed = String.format("%.2f", speedMBps.value)
        )

        errorMessage?.let {
            Text("❌ $it", color = Color.Red, modifier = Modifier.padding(16.dp))
        }

        if (receivingFiles.isNotEmpty()) {
            SectionHeader("📥 Receiving Now")
            Column {
                receivingFiles.forEach { (file, progress) ->
                    FileRowWithProgress(fileName = file, progress = progress, status = "Receiving")
                }
            }
        }

        if (receivedFiles.isNotEmpty()) {
            SectionHeader("✅ Received Files")
            LazyColumn {
                items(receivedFiles) { file ->
                    FileRowWithProgress(fileName = file, progress = 1f, status = "Received")
                }
            }
        }
    }

    // Start Button at bottom
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Button(
            onClick = {
                isReceiving = true
                errorMessage = null

                CoroutineScope(Dispatchers.IO).launch {
                    val startTime = System.currentTimeMillis()
                    try {
                        Client(
                            onStartFile = { fileName, fileSize ->
                                receivingFiles[fileName] = 0f
                            },
                            onProgress = { fileName, bytesReadTotal, fileSize, speed ->
                                totalBytesReceived.value = bytesReadTotal
                                speedMBps.value = speed
                                totalTimeElapsed.value = System.currentTimeMillis() - startTime

                                // update progress of the file
                                if (fileSize > 0) {
                                    val progress = bytesReadTotal.toFloat() / fileSize.toFloat()
                                    receivingFiles[fileName] = progress.coerceIn(0f, 1f)
                                }
                            }
                            ,
                            onFinishFile = { fileName ->
                                receivingFiles.remove(fileName)
                                receivedFiles.add(fileName)
                            }
                        )
                    } catch (e: Exception) {
                        errorMessage = e.message
                        Log.e("Receiving", "Client failed: ${e.message}")
                    }
                }
            },
            enabled = !isReceiving,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Start Receiving")
        }
    }
}


@Composable
fun FileRowWithProgress(fileName: String, progress: Float, status: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = if (status == "Received") Icons.Default.CheckCircle else Icons.Default.FileDownload,
                    contentDescription = null,
                    tint = if (status == "Received") Color(0xFF4CAF50) else Color(0xFF3F51B5)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = fileName,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = status, fontSize = 12.sp, color = Color.Gray)
            }

            if (status != "Received") {
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = progress,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = Color(0xFF3F51B5)
                )
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


