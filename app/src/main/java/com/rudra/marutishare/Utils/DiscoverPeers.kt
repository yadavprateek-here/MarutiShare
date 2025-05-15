package com.rudra.marutishare.Utils

import android.Manifest
import android.net.wifi.p2p.WifiP2pManager
import android.widget.Toast
import androidx.annotation.RequiresPermission
import com.rudra.marutishare.WiFiP2PContext

@RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.NEARBY_WIFI_DEVICES])
fun DiscoverPeers(){
    WiFiP2PContext.manager.discoverPeers(WiFiP2PContext.channel, object : WifiP2pManager.ActionListener {
        override fun onSuccess() {
            Toast.makeText(WiFiP2PContext.context,"Discovery Started!",Toast.LENGTH_SHORT).show()
        }

        override fun onFailure(reason: Int) {
            Toast.makeText(WiFiP2PContext.context,"Discovery Failed To Start!",Toast.LENGTH_SHORT).show()
        }
    })

}