package com.rudra.marutishare.Utils


import android.content.Context
import android.media.MediaScannerConnection
import com.rudra.marutishare.ViewModels
import com.rudra.marutishare.WiFiP2PContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.InetAddress
import java.net.Socket
import kotlin.math.round


suspend fun Client(
    onStartFile: (fileName: String, fileSize: Long) -> Unit,
    onProgress: (fileName: String, bytesReadTotal: Long, fileSize: Long, speedMBps: Double) -> Unit,
    onFinishFile: (fileName: String) -> Unit
) {
    val directory = WiFiP2PContext.context.getOutputDirectory()

    withContext(Dispatchers.IO) {
        val port = 36912
        var clientSocket: Socket? = null

        try {
            val ip = ViewModels.receiveScreenViewModel.groupOwnerIp.value
            clientSocket = Socket(InetAddress.getByName(ip), port)
            clientSocket.soTimeout = 30000

            val inputStream = BufferedInputStream(clientSocket.getInputStream())
            val dataInputStream = DataInputStream(inputStream)

            val numFiles = dataInputStream.readInt()
            ViewModels.receiveScreenViewModel.numberOfFiles = numFiles

            repeat(numFiles) {
                val fileNameLength = dataInputStream.readInt()
                val fileNameBytes = ByteArray(fileNameLength)
                dataInputStream.readFully(fileNameBytes)
                val fileName = String(fileNameBytes)

                val fileSize = dataInputStream.readLong()

                onStartFile(fileName, fileSize)

                val savedSize = saveFileFromStreamWithSpeed(
                    directory, fileName, dataInputStream, fileSize
                ) { bytesReadTotal, speedMBps ->
                    onProgress(fileName, bytesReadTotal, fileSize, speedMBps)
                }
                onFinishFile(fileName)
            }

            clientSocket.close()
            DeleteGroup()
        } catch (e: IOException) {
            clientSocket?.close()
            throw e
        }
    }
}

fun saveFileFromStreamWithSpeed(
    directory: File,
    fileName: String,
    inputStream: DataInputStream,
    fileSize: Long,
    onChunk: (bytesReadTotal: Long, speedMBps: Double) -> Unit
): Long {
    val buffer = ByteArray(8192)
    var bytesRead: Int
    var totalBytesRead = 0L
    val file = File(directory, fileName)
    val outputStream = BufferedOutputStream(FileOutputStream(file))

    var lastTime = System.currentTimeMillis()
    var lastBytes = 0L

    while (totalBytesRead < fileSize) {
        bytesRead = inputStream.read(buffer, 0, minOf(buffer.size.toLong(), fileSize - totalBytesRead).toInt())
        if (bytesRead == -1) break

        outputStream.write(buffer, 0, bytesRead)
        totalBytesRead += bytesRead

        val now = System.currentTimeMillis()
        val elapsed = now - lastTime
        if (elapsed >= 400) {
            val bytesSinceLast = totalBytesRead - lastBytes
            val speedMBps = (bytesSinceLast.toDouble() / (1024 * 1024)) / (elapsed.toDouble() / 1000)
            onChunk(totalBytesRead, speedMBps)

            lastBytes = totalBytesRead
            lastTime = now
        }
    }

    outputStream.flush()
    outputStream.close()
    notifyMediaScanner(WiFiP2PContext.context, file)
    return totalBytesRead
}

fun notifyMediaScanner(context: Context, file: File) {
    MediaScannerConnection.scanFile(
        context,
        arrayOf(file.absolutePath),
        null,
        null
    )
}





