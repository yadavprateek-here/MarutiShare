package com.rudra.marutishare.Utils

import android.content.ContentResolver
import android.net.Uri
import android.util.Log
import com.rudra.marutishare.ViewModels
import com.rudra.marutishare.WiFiP2PContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedOutputStream
import java.io.DataOutputStream
import java.io.IOException
import java.net.ServerSocket


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








