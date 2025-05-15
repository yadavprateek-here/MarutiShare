package com.rudra.marutishare.viewModel

import android.content.Context
import android.net.Uri
import android.net.wifi.p2p.WifiP2pDevice
import android.provider.OpenableColumns
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rudra.marutishare.Utils.Server
import com.rudra.marutishare.Utils.getFileNameFromUri
import com.rudra.marutishare.ViewModels
import com.rudra.marutishare.WiFiP2PContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//class SenderScreenViewModel: ViewModel() {
//    val clientDevice = mutableStateOf<WifiP2pDevice?>(null)
//    val isConnected = mutableStateOf(false)
//
//    val selectedFiles =  mutableStateListOf<Uri>()
//    val fileNameList= mutableListOf<String>()
//    val sentFiles = mutableStateListOf<Uri>()
//    val fileTransferStates = mutableStateListOf<FileTransferState>()
//    val fileTransferSpeeds = mutableStateMapOf<String, Double>()  // FileName -> Speed in MBps
//    val fileTransferStatuses = mutableStateMapOf<String, String>() // FileName -> Status
//
//
//
////    fun addFiles(files:List<Uri>) {
////        Log.d("Files","files selected $files")
////
////        files.forEach { file ->
////            if (!selectedFiles.contains(file)) {
////                selectedFiles.add(file)
////            }
////        }
////    }
//
//    fun addFiles(uris: List<Uri>) {
//        uris.forEach {
//            val name = getFileNameFromUri( it)
//            fileTransferStates.add(FileTransferState(it, name))
//        }
//    }
//
//
//    fun getFileNameFromUri( uri: Uri): String {
//        val context: Context = WiFiP2PContext.context
//        val returnCursor = context.contentResolver.query(uri, null, null, null, null)
//        returnCursor?.use {
//            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
//            if (it.moveToFirst()) {
//                return it.getString(nameIndex)
//            }
//        }
//        return "unknown_file"
//    }
//
//
//
//    fun removeFile(file:Uri){
//        selectedFiles.remove(file)
//
//    }
//
//    fun sendFiles(){
//        viewModelScope.launch(Dispatchers.IO) {
//            Server()
//        }
//    }
//}

class SenderScreenViewModel: ViewModel() {
    val clientDevice = mutableStateOf<WifiP2pDevice?>(null)
    val isConnected = mutableStateOf(false)
    val isServerCalled = mutableStateOf(false)
    val selectedFiles = mutableStateListOf<Uri>()
    val sentFiles = mutableStateListOf<Uri>()
    val fileTransferStates = mutableStateListOf<FileTransferState>()

    fun addFiles(uris: List<Uri>) {
        uris.forEach { uri ->
            if (!selectedFiles.contains(uri)) {
                selectedFiles.add(uri)
                val name = getFileNameFromUri(uri)
                fileTransferStates.add(FileTransferState(uri, name))
            }
        }
    }

    fun getFileNameFromUri(uri: Uri): String {
        val context: Context = WiFiP2PContext.context
        val returnCursor = context.contentResolver.query(uri, null, null, null, null)
        returnCursor?.use {
            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (it.moveToFirst()) {
                return it.getString(nameIndex)
            }
        }
        return "unknown_file"
    }

    fun removeFile(file: Uri) {
        selectedFiles.remove(file)
        fileTransferStates.removeIf { it.uri == file }
    }

    fun sendFiles() {
        viewModelScope.launch(Dispatchers.IO) {
            Server()
        }
    }
}

data class FileTransferState(
    val uri: Uri,
    val fileName: String,
    val status: MutableState<String> = mutableStateOf("Pending"), // <- was just String
    val progress: MutableState<Float> = mutableStateOf(0f),       // <- was just Float
    val speed: MutableState<String> = mutableStateOf("")          // <- was just String
)



