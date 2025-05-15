package com.rudra.marutishare.Utils

import android.content.ContentResolver
import android.content.Context
import android.util.Log
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

//suspend fun Client() {
//
//    val directory = WiFiP2PContext.context.getOutputDirectory()
//    var i = 1
//
//    withContext(Dispatchers.IO) {
//
//        val port:Int = 36912
//        var clientSocket = Socket()
//
//
//        try {
//            // Connect to server
//            Log.d("App", "Client connecting... to ${ViewModels.receiveScreenViewModel.connectedToDevice.value?.deviceAddress}")
//            clientSocket = Socket(InetAddress.getByName(ViewModels.receiveScreenViewModel.groupOwnerIp.value), port)
//            clientSocket.soTimeout = 30000 //30sec
//            Log.d("App", "Client connected i =$i")
//
//            // Receive the number of files from the server
//            val inputStream = clientSocket.getInputStream()
//            val dataInputStream = DataInputStream(inputStream)
//
//            val outputStream = clientSocket.getOutputStream()
//            ViewModels.receiveScreenViewModel.numberOfFiles = dataInputStream.readInt()
//
//            Log.d("App", "Number of files = ${ ViewModels.receiveScreenViewModel.numberOfFiles}")
//
//            // Receive each file
//            for (i in 0 until  ViewModels.receiveScreenViewModel.numberOfFiles) {
//                // Receive the length of the file name
//                val fileNameLength = dataInputStream.readInt()
//                // Receive the file name
//                val fileNameBytes = ByteArray(fileNameLength)
//                dataInputStream.readFully(fileNameBytes)
//                val fileName = String(fileNameBytes)
//                val size = dataInputStream.readLong()
//                Log.d("App", "File name = $fileName")
//                // Receive the file data from the server
//                val fileSize = byteArrayToUri(directory, fileName, clientSocket.getInputStream(),size).toDouble()/1024/1024
//                Log.d("App", "File received: $fileSize MB")
//            }
//            Log.d("App","out of loop")
//            // Close the connection
//            clientSocket.close()
//        } catch (e: IOException) {
//            Log.e("App",  " i = $i: Failed to connect to server: ${e.message}")
//            clientSocket.close()
//            i=1+1
//        }
//    }
//}
//
//
//private fun receiveByteArray(inputStream: InputStream): ByteArray {
//    val dataInputStream = DataInputStream(inputStream)
//    val byteArraySize = dataInputStream.readInt()
//    val byteArray = ByteArray(byteArraySize)
//    dataInputStream.readFully(byteArray)
//    return byteArray
//}
//
//private fun byteArrayToUri( directory: File, fileName: String, inputStream: InputStream, size :Long): Long {
//    val file = File(directory, fileName)
//    val outputStream = FileOutputStream(file)
//    var fileSize = 0L
//
//    try {
//        val startTime = System.currentTimeMillis()
//        var byteArray = receiveByteArray(inputStream)
//        while (byteArray.isNotEmpty()&& fileSize < size) {
//            outputStream.write(byteArray)
//            fileSize += byteArray.size
//
//            val endTime = System.currentTimeMillis()
//            val timeTaken = (endTime - startTime) / 1000.0 // in seconds
//
//            if (fileSize > 0) {
//                ViewModels.receiveScreenViewModel.fileSharingSpeed.floatValue  = (round(fileSize / (timeTaken*1000000) *100)/100).toFloat()
//
//
//            }
//            if (fileSize < size) {
//                byteArray = receiveByteArray(inputStream)
//            }
//        }
//        ViewModels.receiveScreenViewModel.fileSharingSpeed.floatValue =0f
//
//        Log.d("App", "File saved: ${file.absolutePath}, $fileSize bytes")
//    } catch (e: IOException) {
//        Log.e("App", "Failed to save file: ${e.message}")
//    } finally {
//        try {
//            outputStream.close()
//        } catch (e: IOException) {
//            Log.e("App", "Failed to close output stream: ${e.message}")
//        }
//    }
//
//    return fileSize
//}



//suspend fun Client() {
//    val directory = WiFiP2PContext.context.getOutputDirectory()
//
//    withContext(Dispatchers.IO) {
//        val port = 36912
//        var clientSocket: Socket? = null
//
//        try {
//            val ip = ViewModels.receiveScreenViewModel.groupOwnerIp.value
//            Log.d("App", "Client connecting to $ip...")
//            clientSocket = Socket(InetAddress.getByName(ip), port)
//            clientSocket.soTimeout = 30000
//
//            Log.d("App", "Client connected.")
//
//            val inputStream = BufferedInputStream(clientSocket.getInputStream())
//            val dataInputStream = DataInputStream(inputStream)
//
//            val numFiles = dataInputStream.readInt()
//            ViewModels.receiveScreenViewModel.numberOfFiles = numFiles
//            Log.d("App", "Expecting $numFiles files.")
//
//            repeat(numFiles) {
//                val fileNameLength = dataInputStream.readInt()
//                val fileNameBytes = ByteArray(fileNameLength)
//                dataInputStream.readFully(fileNameBytes)
//                val fileName = String(fileNameBytes)
//
//                val fileSize = dataInputStream.readLong()
//                Log.d("App", "Receiving $fileName ($fileSize bytes)")
//
//                val savedSize = saveFileFromStream(directory, fileName, dataInputStream, fileSize)
//                Log.d("App", "Received $fileName (${savedSize / (1024 * 1024.0)} MB)")
//            }
//
//            clientSocket.close()
//            Log.d("App", "All files received. Socket closed.")
//        } catch (e: IOException) {
//            Log.e("App", "Client error: ${e.message}")
//            clientSocket?.close()
//        }
//    }
//}


//suspend fun Client(
//    onFileReceiving: (String) -> Unit,
//    onFileReceived: (String) -> Unit,
//    onError: (String) -> Unit
//) {
//    val directory = WiFiP2PContext.context.getOutputDirectory()
//
//    withContext(Dispatchers.IO) {
//        val port = 36912
//        var clientSocket: Socket? = null
//
//        try {
//            val ip = ViewModels.receiveScreenViewModel.groupOwnerIp.value
//            Log.d("App", "Client connecting to $ip...")
//            clientSocket = Socket(InetAddress.getByName(ip), port)
//            clientSocket.soTimeout = 30000
//
//            Log.d("App", "Client connected.")
//
//            val inputStream = BufferedInputStream(clientSocket.getInputStream())
//            val dataInputStream = DataInputStream(inputStream)
//
//            val numFiles = dataInputStream.readInt()
//            ViewModels.receiveScreenViewModel.numberOfFiles = numFiles
//            Log.d("App", "Expecting $numFiles files.")
//
//            repeat(numFiles) {
//                val fileNameLength = dataInputStream.readInt()
//                val fileNameBytes = ByteArray(fileNameLength)
//                dataInputStream.readFully(fileNameBytes)
//                val fileName = String(fileNameBytes)
//
//                val fileSize = dataInputStream.readLong()
//                Log.d("App", "Receiving $fileName ($fileSize bytes)")
//
//                withContext(Dispatchers.Main) {
//                    onFileReceiving(fileName)
//                }
//
//                val savedSize = saveFileFromStream(directory, fileName, dataInputStream, fileSize)
//
//                Log.d("App", "Received $fileName (${savedSize / (1024 * 1024.0)} MB)")
//
//                withContext(Dispatchers.Main) {
//                    onFileReceived(fileName)
//                }
//            }
//
//            clientSocket.close()
//            Log.d("App", "All files received. Socket closed.")
//        } catch (e: IOException) {
//            Log.e("App", "Client error: ${e.message}")
//            clientSocket?.close()
//            withContext(Dispatchers.Main) {
//                onError(e.message ?: "Unknown error")
//            }
//        }
//    }
//}

//suspend fun Client(
//    onStartFile: (fileName: String) -> Unit,
//    onProgress: (bytesRead: Long, timeElapsedMillis: Long) -> Unit,
//    onFinishFile: (fileName: String) -> Unit
//) {
//    val directory = WiFiP2PContext.context.getOutputDirectory()
//
//    withContext(Dispatchers.IO) {
//        val port = 36912
//        var clientSocket: Socket? = null
//
//        try {
//            val ip = ViewModels.receiveScreenViewModel.groupOwnerIp.value
//            clientSocket = Socket(InetAddress.getByName(ip), port)
//            clientSocket.soTimeout = 30000
//
//            val inputStream = BufferedInputStream(clientSocket.getInputStream())
//            val dataInputStream = DataInputStream(inputStream)
//
//            val numFiles = dataInputStream.readInt()
//            ViewModels.receiveScreenViewModel.numberOfFiles = numFiles
//
//            repeat(numFiles) {
//                val fileNameLength = dataInputStream.readInt()
//                val fileNameBytes = ByteArray(fileNameLength)
//                dataInputStream.readFully(fileNameBytes)
//                val fileName = String(fileNameBytes)
//
//                val fileSize = dataInputStream.readLong()
//
//                onStartFile(fileName)
//
//                val startTime = System.currentTimeMillis()
//                var bytesReadTotal = 0L
//
//                val savedSize = saveFileFromStreamWithSpeed(
//                    directory, fileName, dataInputStream, fileSize
//                ) { chunk ->
//                    bytesReadTotal += chunk
//                    val elapsed = System.currentTimeMillis() - startTime
//                    onProgress(bytesReadTotal, elapsed)
//                }
//
//                onFinishFile(fileName)
//            }
//
//            clientSocket.close()
//        } catch (e: IOException) {
//            clientSocket?.close()
//            throw e
//        }
//    }
//}


suspend fun Client(
    onStartFile: (fileName: String) -> Unit,
    onProgress: (bytesRead: Long, speedMBps: Double) -> Unit,
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

                onStartFile(fileName)

                val startTime = System.currentTimeMillis()
                var bytesReadTotal = 0L

                val savedSize = saveFileFromStreamWithSpeed(
                    directory, fileName, dataInputStream, fileSize
                ) { bytesReadTotal, speedMBps ->
                    onProgress(bytesReadTotal, speedMBps)
                }

                onFinishFile(fileName)
            }

            clientSocket.close()
        } catch (e: IOException) {
            clientSocket?.close()
            throw e
        }
    }
}

suspend fun saveFileFromStreamWithSpeed(
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
    return totalBytesRead
}





private fun saveFileFromStream(
    directory: File,
    fileName: String,
    inputStream: InputStream,
    size: Long
): Long {
    val file = File(directory, fileName)
    var fileSize = 0L

    try {
        FileOutputStream(file).use { outputStream ->
            val buffer = ByteArray(8192)
            var bytesRead: Int
            val startTime = System.currentTimeMillis()

            while (fileSize < size) {
                val remaining = (size - fileSize).coerceAtMost(buffer.size.toLong()).toInt()
                bytesRead = inputStream.read(buffer, 0, remaining)
                if (bytesRead == -1) break

                outputStream.write(buffer, 0, bytesRead)
                fileSize += bytesRead

                val timeTaken = (System.currentTimeMillis() - startTime) / 1000.0
                if (timeTaken > 0) {
                    ViewModels.receiveScreenViewModel.fileSharingSpeed.floatValue =
                        (round(fileSize / (timeTaken * 1000000) * 100) / 100).toFloat() // MB/s
                }
            }
        }

        Log.d("App", "File saved: ${file.absolutePath}, $fileSize bytes")
    } catch (e: IOException) {
        Log.e("App", "Failed to save file: ${e.message}")
    } finally {
        ViewModels.receiveScreenViewModel.fileSharingSpeed.floatValue = 0f
    }

    return fileSize
}



//suspend fun Client() {
//    val directory = WiFiP2PContext.context.getOutputDirectory()
//
//    withContext(Dispatchers.IO) {
//        val port = 36912
//        val serverIp = ViewModels.receiveScreenViewModel.groupOwnerIp.value
//
//        if (serverIp == null) {
//            Log.e("Client", "Group owner IP is null, aborting connection.")
//            return@withContext
//        }
//
//        var clientSocket: Socket? = null
//
//        try {
//            Log.d("Client", "Connecting to server at $serverIp:$port...")
//            clientSocket = Socket(InetAddress.getByName(serverIp), port).apply {
//                soTimeout = 30000 // 30 seconds
//            }
//
//            val dataInputStream = DataInputStream(clientSocket.getInputStream())
//            val numberOfFiles = dataInputStream.readInt()
//            ViewModels.receiveScreenViewModel.numberOfFiles = numberOfFiles
//
//            Log.d("Client", "Receiving $numberOfFiles files")
//
//            repeat(numberOfFiles) {
//                val fileName = readString(dataInputStream)
//                val fileSize = dataInputStream.readLong()
//
//                Log.d("Client", "Receiving file: $fileName, Size: ${fileSize / (1024 * 1024)} MB")
//                val savedSize = receiveFile(directory, fileName, dataInputStream, fileSize)
//                Log.d("Client", "File received: ${"%.2f".format(savedSize / (1024.0 * 1024))} MB")
//            }
//
//            Log.d("Client", "All files received successfully.")
//        } catch (e: IOException) {
//            Log.e("Client", "Connection failed: ${e.message}")
//        } finally {
//            try {
//                clientSocket?.close()
//            } catch (e: IOException) {
//                Log.e("Client", "Failed to close socket: ${e.message}")
//            }
//        }
//    }
//}
//
//
//private fun readString(dataInputStream: DataInputStream): String {
//    val length = dataInputStream.readInt()
//    val bytes = ByteArray(length)
//    dataInputStream.readFully(bytes)
//    return String(bytes)
//}
//
//private fun receiveFile(directory: File, fileName: String, inputStream: DataInputStream, totalSize: Long): Long {
//    val file = File(directory, fileName)
//    FileOutputStream(file).use { output ->
//        var receivedBytes = 0L
//        val startTime = System.currentTimeMillis()
//
//        while (receivedBytes < totalSize) {
//            val chunkSize = inputStream.readInt()
//            val buffer = ByteArray(chunkSize)
//            inputStream.readFully(buffer)
//            output.write(buffer)
//            receivedBytes += chunkSize
//
//            // Speed calc
//            val timeElapsed = (System.currentTimeMillis() - startTime).coerceAtLeast(1) / 1000.0
//            val speedMBps = (receivedBytes / (1024.0 * 1024)) / timeElapsed
//            ViewModels.receiveScreenViewModel.fileSharingSpeed.floatValue = (round(speedMBps * 100) / 100).toFloat()
//        }
//
//        ViewModels.receiveScreenViewModel.fileSharingSpeed.floatValue = 0f
//        return receivedBytes
//    }
//}


