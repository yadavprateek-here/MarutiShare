package com.rudra.marutishare.Utils

import android.annotation.SuppressLint
import android.net.wifi.WpsInfo
import android.net.wifi.p2p.WifiP2pConfig
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pManager
import android.util.Log
import android.widget.Toast
import com.rudra.marutishare.ViewModels
import com.rudra.marutishare.WiFiP2PContext

@SuppressLint("MissingPermission")
fun connectToPeer() {

    val device = ViewModels.receiveScreenViewModel.selectedPeer.value
    if(device==null){
        Log.e("P2P_CONNECT", "---Connection failed due to device is null ---")
        return
    }


    if (device.status == WifiP2pDevice.AVAILABLE) {
        val config = WifiP2pConfig().apply {
            deviceAddress = device.deviceAddress
        }

        WiFiP2PContext.manager.connect(
            WiFiP2PContext.channel,
            config,
            object : WifiP2pManager.ActionListener {
                override fun onSuccess() {
                    Log.d("P2P_CONNECT", "Connection initiated")
//                    ViewModels.receiveScreenViewModel.isConnected.value = true
//                    ViewModels.receiveScreenViewModel.connectedToDevice.value = device
                }

                override fun onFailure(reason: Int) {
                    Log.e("P2P_CONNECT", "Connection failed. Reason: $reason")
                }
            })
    }
    else{
        Toast.makeText(WiFiP2PContext.context,"Not available", Toast.LENGTH_SHORT).show()
    }
}

@SuppressLint("MissingPermission")
fun DataRefresh(){
   WiFiP2PContext.manager.requestGroupInfo(WiFiP2PContext.channel){ gr->
       if(gr!=null) {
           Log.d("WiFiDirect", "Group Members ${gr.clientList}")
           ViewModels.senderScreenViewModel.clientDevice.value =
               gr.clientList.find { d -> !d.isGroupOwner }
           if(!gr.clientList.isNullOrEmpty())
               ViewModels.senderScreenViewModel.isConnected.value = true
       }else{
           Log.d("WiFiDirect", "Group is null")
       }
    }

}
