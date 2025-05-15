package com.rudra.marutishare.UIScreen
import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Send
import androidx.compose.ui.graphics.Color
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.Wifi
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.rudra.marutishare.Nav
import com.rudra.marutishare.Utils.DeleteGroup
import com.rudra.marutishare.Utils.DiscoverPeers
import com.rudra.marutishare.Utils.StopPeerDiscovery
import com.rudra.marutishare.Utils.connectToPeer
import com.rudra.marutishare.ViewModels
import com.rudra.marutishare.viewModel.ReceiveScreenViewModel


@SuppressLint("MissingPermission")
@Composable
fun ReceiveScreen(viewModel: ReceiveScreenViewModel) {
    var isDiscovering by remember { mutableStateOf(true) }

    // Update the list of peers in the view model
    LaunchedEffect(Unit) {
        viewModel.updatePeers()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Main content (peer list and other elements)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(bottom = 80.dp), // Reserve space for the button
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Connect To Device",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Available Devices:",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(12.dp))

            // If no devices are found, display a message
            if (viewModel.peerDevices.isEmpty()) {
                Text("No devices found. Make sure Wi-Fi is ON.")
            } else {
                LazyColumn {
                    items(viewModel.peerDevices) { peer ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clickable {
                                    viewModel.selectedPeer.value = peer
                                    Nav.nav.navigate("receiveFiles")
                                },
                             colors = CardDefaults.cardColors(if(peer.deviceName == viewModel.connectedToDevice.value?.deviceName)Color.Green else Color.Gray),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.Send, contentDescription = null)
                                Spacer(modifier = Modifier.width(12.dp))
                                // Display peer device name
                                Text( "${peer.deviceName}", fontSize = 16.sp)
                            }
                        }
                    }
                }
            }
        }

        // Start/Stop Discovery Button (Fixed at bottom)
        Button(
            onClick = {
                isDiscovering = !isDiscovering
                if (isDiscovering) {
                    DiscoverPeers()
                } else {
                    StopPeerDiscovery()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter) // Position button at the bottom
                .padding(16.dp) // Optional padding around the button
        ) {
            Text(if (isDiscovering) "Stop Discovery" else "Discover Devices")
        }
    }
}





