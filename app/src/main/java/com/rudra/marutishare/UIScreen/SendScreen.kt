package com.rudra.marutishare.UIScreen

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.Icon
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FilePresent
import androidx.compose.material.icons.filled.InsertDriveFile
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rudra.marutishare.Utils.DataRefresh
import com.rudra.marutishare.Utils.DiscoverPeers
import com.rudra.marutishare.Utils.Server
import com.rudra.marutishare.Utils.StopPeerDiscovery
import com.rudra.marutishare.Utils.connectToPeer
import com.rudra.marutishare.Utils.createGroup
import com.rudra.marutishare.Utils.getFileNameFromUri
import com.rudra.marutishare.ViewModels
import com.rudra.marutishare.WiFiP2PContext
import com.rudra.marutishare.viewModel.SenderScreenViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch



@Composable
fun SendScreen(viewModel: SenderScreenViewModel) {

    LaunchedEffect(Unit) {
        createGroup()
    }

    var sendFiles by remember { mutableStateOf(false)}

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.OpenMultipleDocuments()) { uris ->
            viewModel.addFiles(uris)
        }
    if(viewModel.isConnected.value){
        StopPeerDiscovery()
    }


    LaunchedEffect(sendFiles) {
        if (sendFiles) {
            viewModel.sendFiles()
            sendFiles = false // reset after sending
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp)
    ) {

        // Title Section
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Send Files",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )

            // Floating Action Button for Refresh
            IconButton(
                onClick = { DataRefresh() },
                modifier = Modifier
                    .size(48.dp)
                    .background(MaterialTheme.colorScheme.primary, shape = CircleShape)
            ) {
                Icon(Icons.Default.Refresh, contentDescription = null, tint = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Connected Device Status Section
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(4.dp),
            shape = MaterialTheme.shapes.small
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = if (viewModel.isConnected.value)
                        "Connected to: ${viewModel.clientDevice.value?.deviceName}"
                    else "Not Connected",
                    color = if (viewModel.isConnected.value) Color.Gray else Color.Red,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )

            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Files Selected Section
        Text(
            text = "Files Selected",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        if (viewModel.selectedFiles.isEmpty()) {
            Column {
                Text("No Files Selected")
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {
                        launcher.launch(arrayOf("*/*"))
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.AttachFile, contentDescription = "Select Files")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Select Files")
                }
            }

        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                // Files List

                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {

                    items(viewModel.fileTransferStates) { state ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Column(Modifier.padding(16.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.FilePresent, contentDescription = null)
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(
                                        text = state.fileName,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium,
                                        modifier = Modifier.weight(1f),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    IconButton(onClick = { viewModel.removeFile(state.uri) }, enabled = !viewModel.isServerCalled.value) {
                                        Icon(Icons.Default.Delete, contentDescription = "Remove File")
                                    }
                                }
                                // Progress and Status
                                LinearProgressIndicator(
                                progress = { state.progress.value },
                                modifier = Modifier.fillMaxWidth(),
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("Status: ${state.status.value}")
                                if (state.speed.value.isNotEmpty()) {
                                    Text("Speed: ${state.speed.value}")
                                }
                            }
                        }
                    }

                }

                // Add More Files Button
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        launcher.launch(arrayOf("*/*"))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !viewModel.isServerCalled.value
                ) {
                    Icon(Icons.Default.AttachFile, contentDescription = "Add More Files")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Add More Files")
                }

                // Spacer and Send Button pinned to bottom
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                       sendFiles = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                    , enabled = viewModel.isConnected.value,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White,
                        disabledContainerColor = Color.Gray,
                        disabledContentColor = Color.Black
                    )
                ) {
                    Icon(Icons.Default.Send, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(if(viewModel.isConnected.value)"Send Files" else "Not Connected")
                }
            }
        }
    }

}



