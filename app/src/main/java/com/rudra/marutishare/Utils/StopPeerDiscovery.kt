package com.rudra.marutishare.Utils

import android.content.Context
import android.net.wifi.p2p.WifiP2pManager
import android.util.Log
import android.widget.Toast
import com.rudra.marutishare.WiFiP2PContext
import kotlin.time.Duration

fun StopPeerDiscovery(){

    WiFiP2PContext.manager.stopPeerDiscovery(WiFiP2PContext.channel,object : WifiP2pManager.ActionListener {
        override fun onSuccess() {
            Log.d("WiFiDirect","Discovery Stopped")
            Toast.makeText(WiFiP2PContext.context,"Discovery Stopped!",Toast.LENGTH_SHORT).show()
        }

        override fun onFailure(reason: Int) {
            Log.d("WiFiDirect","Couldn't stop Discovery due to ${reason}")
            Toast.makeText(WiFiP2PContext.context,"Discovery Failed To Stop!",Toast.LENGTH_SHORT).show()

        }
    })
}