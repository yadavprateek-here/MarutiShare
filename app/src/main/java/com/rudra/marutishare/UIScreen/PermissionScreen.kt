package com.rudra.marutishare.UIScreen

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.net.wifi.WifiManager
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rudra.marutishare.Nav

@Composable
fun PermissionScreen(onPermissionsGrantedAndReady: () -> Unit) {
    val context = LocalContext.current
    val activity = context as Activity
    val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    val showEnablePrompt = remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.values.all { it }
        if (allGranted) {
            // Now check if Wi-Fi and Location are ON
            showEnablePrompt.value = !(wifiManager.isWifiEnabled && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        } else {
            Toast.makeText(context, "Grant all permissions to proceed", Toast.LENGTH_SHORT).show()
        }
    }

    val requiredPermissions = remember {
        mutableListOf(
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.INTERNET
        ).apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                add(Manifest.permission.NEARBY_WIFI_DEVICES)
            } else {
                add(Manifest.permission.ACCESS_FINE_LOCATION)
                add(Manifest.permission.ACCESS_COARSE_LOCATION)
            }

            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
                add(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }

    LaunchedEffect(Unit) {
        permissionLauncher.launch(requiredPermissions.toTypedArray())
    }

    if (showEnablePrompt.value) {
        EnableWifiAndLocationPrompt(
            onRetry = {
                val wifiOn = wifiManager.isWifiEnabled
                val locationOn = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

                if (wifiOn && locationOn) {
                    onPermissionsGrantedAndReady()
                } else {
                    Toast.makeText(context, "Please enable Wi-Fi and Location", Toast.LENGTH_SHORT).show()
                }
            }
        )
    } else {
        Nav.nav.navigate("home")
//        Box(
//            modifier = Modifier.fillMaxSize(),
//            contentAlignment = Alignment.Center
//        ) {
//            Text("Requesting Permissions...", fontSize = 18.sp, fontWeight = FontWeight.Medium)
//        }
    }
}

@Composable
fun EnableWifiAndLocationPrompt(onRetry: () -> Unit) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(Icons.Default.Warning, contentDescription = null, tint = Color.Red, modifier = Modifier.size(64.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Text("Wi-Fi or Location is turned off", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Please enable both to proceed", textAlign = TextAlign.Center)

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            context.startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
        }) {
            Text("Go to Wi-Fi Settings")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = {
            context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }) {
            Text("Go to Location Settings")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}


