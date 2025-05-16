package com.rudra.marutishare.viewModel

import android.net.wifi.p2p.WifiP2pDevice
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.rudra.marutishare.WiFiP2PContext

class ReceiveScreenViewModel : ViewModel() {
    val peerDevices = mutableStateListOf<WifiP2pDevice>()
    val connectedToDevice = mutableStateOf<WifiP2pDevice?>(null)
    val isConnected = mutableStateOf(false)
    val groupOwnerIp = mutableStateOf("")
    val selectedPeer = mutableStateOf<WifiP2pDevice?>(null)
    var numberOfFiles = 0
    var fileSharingSpeed = mutableFloatStateOf(0f)



    fun updatePeers() {
        peerDevices.clear()
        peerDevices.addAll(WiFiP2PContext.peerList)
    }

    fun reset(){
        connectedToDevice.value = null
        isConnected.value = false
        groupOwnerIp.value=""
        selectedPeer.value = null

    }
}
