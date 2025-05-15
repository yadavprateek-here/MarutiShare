package com.rudra.marutishare.Utils

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.InsertDriveFile
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.InsertDriveFile
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rudra.marutishare.ViewModels
import com.rudra.marutishare.WiFiP2PContext
import com.rudra.marutishare.WiFiP2PContext.context
import com.rudra.marutishare.viewModel.SenderScreenViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedOutputStream
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.ServerSocket
import java.net.Socket

//suspend fun Server() {
//
//    val port:Int = 36912
//    val uris = ViewModels.senderScreenViewModel.selectedFiles
//    val fileNames = ViewModels.senderScreenViewModel.fileNameList
//    withContext(Dispatchers.IO) {
//        val serverSocket= ServerSocket(port)
//
//        try {
//            // Create server socket
//            //serverSocket =
//            Log.d("App", "Server on")
//            serverSocket.soTimeout = 30000
//            val clientSocket = serverSocket.accept()
//            // Wait for a client to connect
//            Log.d("App", "Client connected")
//
//            val clientAddress = clientSocket.inetAddress.hostAddress
//            val clientPort = clientSocket.port
//            Log.d("App", "Client connected: $clientAddress:$clientPort")
//
//            // Send the number of files to the client.
//            val numFiles = uris.size
//            val outputStream = clientSocket.getOutputStream()
//            val inputStream = clientSocket.getInputStream()
//            val dataInputStream = DataInputStream(inputStream)
//            val dataOutputStream = DataOutputStream(outputStream)
//            dataOutputStream.writeInt(numFiles)
//
//            //-----------------
//            // Send each file
//
//            for (i in uris.indices) {
//
//                val uri = uris[i]
//                val fileName = fileNames[i]
//                Log.d("App","file name  = $fileName")
//
//                // Send the file name and size
//                dataOutputStream.writeInt(fileName.length)
//                dataOutputStream.writeBytes(fileName)
//                val inputStream = context.contentResolver.openInputStream(uri)
//                val size = inputStream?.available()?.toLong() ?: -1
//                inputStream?.close()
//                dataOutputStream.writeLong(size)
//
//                // Send the file contents
//                uriToByteArray(context.contentResolver, uri, outputStream)
//                dataOutputStream.flush()
//
//            }
//            // Close the connection
//            clientSocket.close()
//            serverSocket.close()
//        } catch (e: IOException) {
//            Log.e("App", "Failed to start server: ${e.message}")
//            serverSocket.close()
//
//        }
//    }
//}
//
//
//private fun sendByteArray(outputStream: OutputStream, byteArray: ByteArray) {
//    val dataOutputStream = DataOutputStream(outputStream)
//    dataOutputStream.writeInt(byteArray.size)
//    dataOutputStream.write(byteArray)
//
//}
//
//
//private fun uriToByteArray(contentResolver: ContentResolver, uri: Uri, outputStream: OutputStream):Long{
//    var fileSize = 0L
//    var inputStream: InputStream? = null
//    val BUFFER_SIZE = 8192
//
//
//    try {
//        inputStream = contentResolver.openInputStream(uri)
//        val buffer = ByteArray(BUFFER_SIZE)
//        var bytesRead: Int
//
//        if (inputStream != null) {
//
//            var totalBytesRead = 0L
//
//            val startTime = System.currentTimeMillis()
//
//            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
//                totalBytesRead += bytesRead
//                sendByteArray(outputStream, buffer.copyOf(bytesRead))
//                fileSize += bytesRead
//                val endTime = System.currentTimeMillis()
//                val timeTaken = (endTime - startTime) / 1000.0 // in seconds
//                if (totalBytesRead > 0) {
//                    val speed = totalBytesRead / (timeTaken*1000000) // in bytes per second
//                    println("Transfer speed: $speed MB/second")
//                }
//            }
//
//        }
//
//
//        Log.d("App", "File read: $fileSize bytes")
//    } catch (e: IOException) {
//        Log.e("App", "Failed to read file: ${e.message}")
//    }
//
//
//    return  fileSize
//}


//suspend fun Server() {
//    val port = 36912
//    val uris = ViewModels.senderScreenViewModel.selectedFiles
//    val fileNames = ViewModels.senderScreenViewModel.fileNameList
//
//    withContext(Dispatchers.IO) {
//        val serverSocket = ServerSocket(port)
//        try {
//            Log.d("App", "Server started on port $port")
//            serverSocket.soTimeout = 30000
//
//            val clientSocket = serverSocket.accept()
//            Log.d("App", "Client connected: ${clientSocket.inetAddress.hostAddress}:${clientSocket.port}")
//
//            val outputStream = clientSocket.getOutputStream()
//            val dataOutputStream = DataOutputStream(BufferedOutputStream(outputStream))
//
//            // Send total number of files
//            dataOutputStream.writeInt(uris.size)
//
//
//            val transferStates = ViewModels.senderScreenViewModel.fileTransferStates
//
//            for (i in uris.indices) {
//                val uri = uris[i]
//                val fileName = fileNames[i]
//                val state = transferStates.find { it.uri == uri }
//                state?.status = "Sending"
//
//                val fileSize = getFileSize(context.contentResolver, uri)
//
//                dataOutputStream.writeInt(fileName.toByteArray().size)
//                dataOutputStream.write(fileName.toByteArray())
//                dataOutputStream.writeLong(fileSize)
//
//                sendFileBytes(context.contentResolver, uri, dataOutputStream) { progress, speed ->
//                    state?.progress = progress
//                    state?.speed = speed
//                }
//
//                state?.status = "Sent"
//                ViewModels.senderScreenViewModel.sentFiles.add(uri)
//            }
//
//
//            dataOutputStream.flush()
//            clientSocket.close()
//            serverSocket.close()
//            Log.d("App", "Transfer complete and sockets closed.")
//
//        } catch (e: IOException) {
//            Log.e("App", "Error during transfer: ${e.message}")
//            serverSocket.close()
//        }
//    }
//}
//suspend fun Server() {
//    val port = 36912
//    val uris = ViewModels.senderScreenViewModel.selectedFiles
//    val fileNames = ViewModels.senderScreenViewModel.fileNameList
//
//    withContext(Dispatchers.IO) {
//        val serverSocket = ServerSocket(port)
//        try {
//            val clientSocket = serverSocket.accept()
//            val dataOutputStream = DataOutputStream(BufferedOutputStream(clientSocket.getOutputStream()))
//            val contentResolver = WiFiP2PContext.context.contentResolver
//
//            dataOutputStream.writeInt(uris.size)
//
//            for (i in uris.indices) {
//                val uri = uris[i]
//                val fileName = fileNames[i]
//                ViewModels.senderScreenViewModel.fileTransferStatuses[fileName] = "In Progress"
//
//                val fileSize = getFileSize(contentResolver, uri)
//
//                dataOutputStream.writeInt(fileName.toByteArray().size)
//                dataOutputStream.write(fileName.toByteArray())
//                dataOutputStream.writeLong(fileSize)
//
//                var bytesSent = 0L
//                val buffer = ByteArray(8192)
//                val startTime = System.currentTimeMillis()
//                var lastTime = startTime
//                var lastBytes = 0L
//
//                contentResolver.openInputStream(uri)?.use { inputStream ->
//                    var bytesRead: Int
//                    while (inputStream.read(buffer).also { bytesRead = it } != -1) {
//                        dataOutputStream.write(buffer, 0, bytesRead)
//                        bytesSent += bytesRead
//
//                        val now = System.currentTimeMillis()
//                        val timeElapsed = now - lastTime
//                        if (timeElapsed >= 400) {
//                            val bytesDelta = bytesSent - lastBytes
//                            val speed = (bytesDelta / 1024.0 / 1024.0) / (timeElapsed / 1000.0)
//                            ViewModels.senderScreenViewModel.fileTransferSpeeds[fileName] = speed
//                            lastTime = now
//                            lastBytes = bytesSent
//                        }
//                    }
//                }
//
//                ViewModels.senderScreenViewModel.fileTransferStatuses[fileName] = "Sent"
//                ViewModels.senderScreenViewModel.sentFiles.add(uri)
//            }
//
//            dataOutputStream.flush()
//            clientSocket.close()
//            serverSocket.close()
//
//        } catch (e: IOException) {
//            Log.e("App", "Sending failed: ${e.message}")
//            serverSocket.close()
//        }
//    }
//}
//
//
//
//
//
//private fun getFileSize(contentResolver: ContentResolver, uri: Uri): Long {
//    return contentResolver.openAssetFileDescriptor(uri, "r")?.use {
//        it.length
//    } ?: -1L
//}

//private fun sendFileBytes(contentResolver: ContentResolver, uri: Uri, outputStream: OutputStream) {
//    val buffer = ByteArray(8192)
//    var inputStream: InputStream? = null
//
//    try {
//        inputStream = contentResolver.openInputStream(uri)
//        var bytesRead: Int
//
//        while (inputStream?.read(buffer).also { bytesRead = it ?: -1 } != -1) {
//            outputStream.write(buffer, 0, bytesRead)
//        }
//
//    } catch (e: IOException) {
//        Log.e("App", "Error sending file: ${e.message}")
//    } finally {
//        inputStream?.close()
//    }
//}

//suspend fun sendFileBytes(
//    contentResolver: ContentResolver,
//    uri: Uri,
//    outputStream: OutputStream,
//    onProgress: (Float, String) -> Unit
//) {
//    val buffer = ByteArray(8192)
//    val totalBytes = getFileSize(contentResolver, uri)
//    var bytesSent = 0L
//    var startTime = System.currentTimeMillis()
//
//    contentResolver.openInputStream(uri)?.use { inputStream ->
//        var bytesRead: Int
//        while (inputStream.read(buffer).also { bytesRead = it } != -1) {
//            outputStream.write(buffer, 0, bytesRead)
//            bytesSent += bytesRead
//
//            // Calculate progress and speed
//            val progress = bytesSent.toFloat() / totalBytes
//            val timeElapsed = (System.currentTimeMillis() - startTime).coerceAtLeast(1) // avoid divide-by-zero
//            val speed = bytesSent / (timeElapsed / 1000.0) // bytes per second
//
//            val speedStr = if (speed >= 1024 * 1024) {
//                "%.2f MB/s".format(speed / (1024 * 1024))
//            } else {
//                "%.1f KB/s".format(speed / 1024)
//            }
//
//            onProgress(progress, speedStr)
//        }
//    }
//}


suspend fun Server() {
    val port = 36912
    val states = ViewModels.senderScreenViewModel.fileTransferStates
    ViewModels.senderScreenViewModel.isServerCalled.value = true
    withContext(Dispatchers.IO) {
        val serverSocket = ServerSocket(port)
        try {
            val clientSocket = serverSocket.accept()
            val dataOutputStream = DataOutputStream(BufferedOutputStream(clientSocket.getOutputStream()))
            val contentResolver = WiFiP2PContext.context.contentResolver

            dataOutputStream.writeInt(states.size)

            for (state in states) {
                val uri = state.uri
                val fileName = state.fileName

                val fileSize = getFileSize(contentResolver, uri)
                state.status.value = "Sending"

                dataOutputStream.writeInt(fileName.toByteArray().size)
                dataOutputStream.write(fileName.toByteArray())
                dataOutputStream.writeLong(fileSize)

                var bytesSent = 0L
                val buffer = ByteArray(8192)
                val startTime = System.currentTimeMillis()
                var lastTime = startTime
                var lastBytes = 0L

                contentResolver.openInputStream(uri)?.use { inputStream ->
                    var bytesRead: Int
                    while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                        dataOutputStream.write(buffer, 0, bytesRead)
                        bytesSent += bytesRead

                        // Update progress
                        state.progress.value = bytesSent.toFloat() / fileSize.toFloat()

                        // Update speed every 400ms
                        val now = System.currentTimeMillis()
                        val timeElapsed = now - lastTime
                        if (timeElapsed >= 400) {
                            val bytesDelta = bytesSent - lastBytes
                            val speed = (bytesDelta / 1024.0 / 1024.0) / (timeElapsed / 1000.0)
                            state.speed.value = String.format("%.2f MB/s", speed)
                            lastTime = now
                            lastBytes = bytesSent
                        }
                    }
                }

                state.status.value = "Sent"
                ViewModels.senderScreenViewModel.sentFiles.add(uri)
            }

            dataOutputStream.flush()
            clientSocket.close()
            serverSocket.close()

        } catch (e: IOException) {
            Log.e("App", "Sending failed: ${e.message}")
            serverSocket.close()

            // Optional: mark failed in all file states
            states.forEach { it.status.value = "Failed" }
        }
    }
}


private fun getFileSize(contentResolver: ContentResolver, uri: Uri): Long {
    return contentResolver.openAssetFileDescriptor(uri, "r")?.use {
        it.length
    } ?: -1L
}








