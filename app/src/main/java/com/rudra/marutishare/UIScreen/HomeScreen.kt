package com.rudra.marutishare.UIScreen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Send
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import com.rudra.marutishare.Nav
import com.rudra.marutishare.Utils.DiscoverPeers
import com.rudra.marutishare.WiFiP2PContext

//@SuppressLint("MissingPermission")
//@Composable
//fun HomeScreen(
//    onSendClick: () -> Unit,
//    onReceiveClick: () -> Unit
//) {
//    LaunchedEffect(Unit) {
//        DiscoverPeers()
//    }
//
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(24.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(
//            text = "Maruti Share",
//            fontSize = 32.sp,
//            fontWeight = FontWeight.Bold
//        )
//
//        Spacer(modifier = Modifier.height(12.dp))
//
//        Text(
//            text = "Share files wirelessly with blazing speed!",
//            fontSize = 16.sp,
//            color = Color.Gray
//        )
//
//        Spacer(modifier = Modifier.height(48.dp))
//
//        Button(
//            onClick = onSendClick,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Icon(Icons.Default.Send, contentDescription = "Send")
//            Spacer(modifier = Modifier.width(8.dp))
//            Text("Send Files")
//        }
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        Button(
//            onClick = onReceiveClick,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Icon(Icons.Default.Download, contentDescription = "Receive")
//            Spacer(modifier = Modifier.width(8.dp))
//            Text("Receive Files")
//        }
//    }
//}

//@SuppressLint("MissingPermission")
//@Composable
//fun HomeScreen(
//    onSendClick: () -> Unit,
//    onReceiveClick: () -> Unit
//) {
//    LaunchedEffect(Unit) {
//        DiscoverPeers()
//    }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(
//                Brush.verticalGradient(
//                    colors = listOf(Color(0xFF4A90E2), Color(0xFF50E3C2))
//                )
//            ),
//        contentAlignment = Alignment.Center
//    ) {
//        Card(
//            shape = RoundedCornerShape(24.dp),
//            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
//            modifier = Modifier
//                .padding(24.dp)
//                .fillMaxWidth()
//        ) {
//            Column(
//                modifier = Modifier
//                    .padding(24.dp),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Icon(
//                    imageVector = Icons.Default.Share,
//                    contentDescription = "App Icon",
//                    tint = Color(0xFF4A90E2),
//                    modifier = Modifier
//                        .size(72.dp)
//                        .padding(bottom = 16.dp)
//                )
//
//                Text(
//                    text = "Maruti Share",
//                    fontSize = 28.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color(0xFF222222)
//                )
//
//                Spacer(modifier = Modifier.height(8.dp))
//
//                Text(
//                    text = "Share files wirelessly with blazing speed!",
//                    fontSize = 16.sp,
//                    color = Color.Gray,
//                    textAlign = TextAlign.Center
//                )
//
//                Spacer(modifier = Modifier.height(32.dp))
//
//                Button(
//                    onClick = onSendClick,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(52.dp),
//                    shape = RoundedCornerShape(16.dp)
//                ) {
//                    Icon(Icons.Default.Send, contentDescription = "Send")
//                    Spacer(modifier = Modifier.width(8.dp))
//                    Text("Send Files", fontSize = 16.sp)
//                }
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                Button(
//                    onClick = onReceiveClick,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(52.dp),
//                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF50E3C2)),
//                    shape = RoundedCornerShape(16.dp)
//                ) {
//                    Icon(Icons.Default.Download, contentDescription = "Receive")
//                    Spacer(modifier = Modifier.width(8.dp))
//                    Text("Receive Files", fontSize = 16.sp)
//                }
//            }
//        }
//    }
//}

//@SuppressLint("MissingPermission")
//@Composable
//fun HomeScreen(
//    onSendClick: () -> Unit,
//    onReceiveClick: () -> Unit
//) {
//    val context = LocalContext.current
//
//    LaunchedEffect(Unit) {
//        DiscoverPeers()
//    }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(
//                Brush.verticalGradient(
//                    colors = listOf(Color(0xFF4A90E2), Color(0xFF50E3C2))
//                )
//            ),
//        contentAlignment = Alignment.Center
//    ) {
//        Card(
//            shape = RoundedCornerShape(24.dp),
//            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
//            modifier = Modifier
//                .padding(24.dp)
//                .fillMaxWidth()
//        ) {
//            Column(
//                modifier = Modifier
//                    .padding(24.dp),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Icon(
//                    imageVector = Icons.Default.Share,
//                    contentDescription = "App Icon",
//                    tint = Color(0xFF4A90E2),
//                    modifier = Modifier
//                        .size(72.dp)
//                        .padding(bottom = 16.dp)
//                )
//
//                Text(
//                    text = "Maruti Share",
//                    fontSize = 28.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color(0xFF222222)
//                )
//
//                Spacer(modifier = Modifier.height(8.dp))
//
//                Text(
//                    text = "Share files wirelessly with blazing speed!",
//                    fontSize = 16.sp,
//                    color = Color.Gray,
//                    textAlign = TextAlign.Center
//                )
//
//                Spacer(modifier = Modifier.height(32.dp))
//
//                Button(
//                    onClick = onSendClick,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(52.dp),
//                    shape = RoundedCornerShape(16.dp)
//                ) {
//                    Icon(Icons.Default.Send, contentDescription = "Send")
//                    Spacer(modifier = Modifier.width(8.dp))
//                    Text("Send Files", fontSize = 16.sp)
//                }
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                Button(
//                    onClick = onReceiveClick,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(52.dp),
//                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF50E3C2)),
//                    shape = RoundedCornerShape(16.dp)
//                ) {
//                    Icon(Icons.Default.Download, contentDescription = "Receive")
//                    Spacer(modifier = Modifier.width(8.dp))
//                    Text("Receive Files", fontSize = 16.sp)
//                }
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                Button(
//                    onClick = { Nav.nav.navigate("viewReceivedFiles") },
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    Icon(Icons.Default.FolderOpen, contentDescription = "View Files")
//                    Spacer(modifier = Modifier.width(8.dp))
//                    Text("View Received Files")
//                }
//            }
//        }
//    }
//}

@SuppressLint("MissingPermission")
@Composable
fun HomeScreen(
    onSendClick: () -> Unit,
    onReceiveClick: () -> Unit,
    onViewReceivedClick: () -> Unit
) {
    LaunchedEffect(Unit) {
        DiscoverPeers()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF4A90E2), Color(0xFF50E3C2))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = "App Icon",
                tint = Color.White,
                modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Maruti Share",
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Share files wirelessly with blazing speed!",
                fontSize = 18.sp,
                color = Color.White.copy(alpha = 0.8f),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Send Button
            GradientButton(
                text = "Send Files",
                icon = Icons.Default.Send,
                onClick = onSendClick,
                gradient = Brush.horizontalGradient(listOf(Color(0xFF56CCF2), Color(0xFF2F80ED)))
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Receive Button
            GradientButton(
                text = "Receive Files",
                icon = Icons.Default.Download,
                onClick = onReceiveClick,
                gradient = Brush.horizontalGradient(listOf(Color(0xFF56D8C1), Color(0xFF2DC3A5)))
            )

            Spacer(modifier = Modifier.height(20.dp))

            // View Received Files Button
            OutlinedButton(
                onClick = onViewReceivedClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                border = BorderStroke(2.dp, Color.White),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
            ) {
                Icon(Icons.Default.FolderOpen, contentDescription = "View Files")
                Spacer(modifier = Modifier.width(8.dp))
                Text("View Received Files", fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun GradientButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    gradient: Brush
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        contentPadding = PaddingValues()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient, shape = RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(icon, contentDescription = null, tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text, fontSize = 16.sp, color = Color.White)
            }
        }
    }
}



@Composable
@Preview(showBackground = true, showSystemUi = true)
fun check(){
    HomeScreen(onSendClick = {}, onReceiveClick = {}, onViewReceivedClick = {})
}

