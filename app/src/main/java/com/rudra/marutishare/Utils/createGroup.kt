package com.rudra.marutishare.Utils

import android.annotation.SuppressLint
import android.net.wifi.p2p.WifiP2pManager
import android.net.wifi.p2p.WifiP2pConfig
import android.util.Log
import com.rudra.marutishare.WiFiP2PContext

@SuppressLint("MissingPermission")
fun createGroup() {
    WiFiP2PContext.manager.createGroup(WiFiP2PContext.channel, object : WifiP2pManager.ActionListener {
        override fun onSuccess() {
            Log.d("Wi-Fi P2P", "Group created successfully")
            // The device is now the group owner
            // You can start your file transfer logic here, as the group owner
        }

        override fun onFailure(reason: Int) {
            Log.d("Wi-Fi P2P", "Failed to create group: $reason")
            DeleteGroup()
            createGroup()
            // Handle failure
        }
    })
}
