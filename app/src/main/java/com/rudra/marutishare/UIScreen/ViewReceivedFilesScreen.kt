package com.rudra.marutishare.UIScreen

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.InsertDriveFile
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.InsertDriveFile
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.rudra.marutishare.WiFiP2PContext
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewReceivedFilesScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val filesDir = WiFiP2PContext.context.getOutputDirectory()
    val files = remember { filesDir.listFiles()?.toList() ?: emptyList() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Received Files") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        if (files.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("No files received yet.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                items(files) { file ->
                    FileItem(file = file, onOpen = {
                        openFile(WiFiP2PContext.context, file)
                    })
                }
            }
        }
    }
}

@Composable
fun FileItem(file: File, onOpen: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .clickable { onOpen() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.InsertDriveFile, contentDescription = "File", tint = Color.Blue)
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = file.name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}


//fun openFile(context: Context, file: File) {
//    try {
//        val fileUri = FileProvider.getUriForFile(
//            context,
//            "${context.packageName}.provider",
//            file
//        )
//
//        val mimeType = context.contentResolver.getType(fileUri) ?: "*/*"
//
//        val intent = Intent(Intent.ACTION_VIEW).apply {
//            setDataAndType(fileUri, mimeType)
//            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//        }
//
//        context.startActivity(intent)
//    } catch (e: ActivityNotFoundException) {
//        Toast.makeText(context, "No app found to open this file", Toast.LENGTH_SHORT).show()
//    } catch (e: Exception) {
//        Toast.makeText(context, "Failed to open file", Toast.LENGTH_SHORT).show()
//    }
//}

fun openFile(context: Context, file: File) {
    try {
        Log.d("OpenFile", "File exists: ${file.exists()}, path: ${file.absolutePath}")

        val fileUri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            file
        )
        Log.d("OpenFile", "File Uri: $fileUri")

        val mimeType = context.contentResolver.getType(fileUri) ?: "*/*"
        Log.d("OpenFile", "MimeType: $mimeType")

        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(fileUri, mimeType)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(context, "No app found to open this file", Toast.LENGTH_SHORT).show()
    } catch (e: Exception) {
        Log.e("OpenFile", "Error opening file", e)
        Toast.makeText(context, "Failed to open file", Toast.LENGTH_SHORT).show()
    }
}

