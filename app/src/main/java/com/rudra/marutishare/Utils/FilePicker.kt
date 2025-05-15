package com.rudra.marutishare.Utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat

@Composable
fun FilePicker(onFilesSelected: (List<Uri>) -> Unit) {
    val context = LocalContext.current

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.OpenMultipleDocuments()) { uris ->
            onFilesSelected(uris)
        }
    Box(
        modifier = Modifier
            .padding(10.dp)
    ) {
        Button(modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .width(120.dp)
            .align(Alignment.BottomCenter),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White
            ),
            onClick = {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(
                            context as Activity,
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            1
                        )
                    } else {
                        launcher.launch(arrayOf("*/*"))
                    }
                } else {
                    launcher.launch(arrayOf("*/*"))
                }
            }
        ) {
            Text(text = "Select files")
        }

    }

}