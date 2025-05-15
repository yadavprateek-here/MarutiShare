package com.rudra.marutishare.Utils

import android.net.wifi.p2p.WifiP2pManager
import android.util.Log
import com.rudra.marutishare.ViewModels
import com.rudra.marutishare.WiFiP2PContext

fun DeleteGroup(){
    WiFiP2PContext.manager.removeGroup(WiFiP2PContext.channel, object : WifiP2pManager.ActionListener {
        override fun onSuccess() {
            Log.d("Wi-Fi P2P", "Group is removed group")
            ViewModels.receiveScreenViewModel.isConnected.value = false
            ViewModels.receiveScreenViewModel.connectedToDevice.value = null
        }

        override fun onFailure(reason: Int) {
            Log.d("Wi-Fi P2P", "Failed to remove group: $reason")
        }
    })
}

