package com.rudra.marutishare.Utils

import android.Manifest
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.NetworkInfo
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pManager
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresPermission
import com.rudra.marutishare.ViewModels
import com.rudra.marutishare.WiFiP2PContext


class WiFiDirectBroadcastReceiver(
    private val manager: WifiP2pManager,
    private val channel: WifiP2pManager.Channel,
    private val activity: Activity
) : BroadcastReceiver() {

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.NEARBY_WIFI_DEVICES])
    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION -> {
                val state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1)
                if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                    Toast.makeText(context, "Wi-Fi Direct is ON", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(context, "Wi-Fi Direct is OFF", Toast.LENGTH_SHORT).show()
                }
            }

            WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION -> {
                // Request available peers from the wifi p2p manager
                manager.requestPeers(channel) { peers ->
                    // You can now access `peers.deviceList`
                    WiFiP2PContext.peerList = peers.deviceList
                    ViewModels.receiveScreenViewModel.updatePeers()
                    Log.d("WiFiDirect", "Peers changed: ${peers.deviceList}")

                }
            }

//            WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION -> {
//                val networkInfo = intent.getParcelableExtra<NetworkInfo>(WifiP2pManager.EXTRA_NETWORK_INFO)
//
//                if (networkInfo?.isConnected == true) {
//                    manager.requestConnectionInfo(channel) { info ->
//
//                        if (info.groupFormed && info.isGroupOwner) {
//                            // ✅ Group Owner knows a client has connected
//                            // But cannot directly get client deviceName here
//                            Log.d("WiFiDirect", "Client connected to me. Group owner: true")
//
//                            manager.requestGroupInfo(channel){ gr->
//                                Log.d("WiFiDirect", "Group Members ${gr.clientList}")
//
//                                ViewModels.senderScreenViewModel.clientDevice.value = gr.clientList.find { d-> !d.isGroupOwner }
//                                if(!gr.clientList.isNullOrEmpty())
//                                    ViewModels.senderScreenViewModel.isConnected.value = true
//                            }
//
//                            // Optionally update a ViewModel or trigger some UI state
//                        }
//                        else if(!info.isGroupOwner) {
//                            val peerDevice =
//                                ViewModels.receiveScreenViewModel.peerDevices.find { it.deviceAddress == info.groupOwnerAddress.toString() }
//
//                            ViewModels.receiveScreenViewModel.isConnected.value = true
//                            ViewModels.receiveScreenViewModel.connectedToDevice.value = peerDevice
//                        }
//
//
//
//                    }
//                }
//
////
//            }


            WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION -> {
                val networkInfo = intent.getParcelableExtra<NetworkInfo>(WifiP2pManager.EXTRA_NETWORK_INFO)

                if (networkInfo?.isConnected == true) {
                    // Connection is established — Now determine role
                    manager.requestConnectionInfo(channel) { info ->
                        if (info.groupFormed) {
                            if (info.isGroupOwner) {
                                // 📲 DEVICE A: Group Owner (Sender)
                                Log.d("WiFiDirect", "Group formed. This device is Group Owner.")

                                manager.requestGroupInfo(channel) { group ->
                                    val clients = group?.clientList
                                    Log.d("WiFiDirect", "Connected Clients: $clients")

                                    if (!clients.isNullOrEmpty()) {
                                        // ✅ Client connected!
                                        ViewModels.senderScreenViewModel.clientDevice.value = clients.first()
                                        ViewModels.senderScreenViewModel.isConnected.value = true
                                    } else {
                                        Log.d("WiFiDirect", "No clients connected yet.")
                                    }
                                }
                            } else {


//                                // 📲 DEVICE B: Client (Receiver)
//                                Log.d("WiFiDirect", "Group formed. This device is Client.")
//
//                                val groupOwnerIp = info.groupOwnerAddress.hostAddress
//                                Log.d("WiFiDirect", "Connected to Group Owner at $groupOwnerIp")
//
//                                // Optional: match device by MAC
//                                val ownerDevice = ViewModels.receiveScreenViewModel.peerDevices.find {
//                                    it.deviceAddress.equals(ViewModels.receiveScreenViewModel.groupOwnerMacAddress.value, ignoreCase = true)
//                                }
//
//                                ViewModels.receiveScreenViewModel.connectedToDevice.value = ownerDevice
//                                ViewModels.receiveScreenViewModel.isConnected.value = true


                                Log.d("WiFiDirect", "Connected as Client. Group Owner IP: ${info.groupOwnerAddress.hostAddress}")

                                ViewModels.receiveScreenViewModel.groupOwnerIp.value = info.groupOwnerAddress.hostAddress
                                ViewModels.receiveScreenViewModel.isConnected.value = true

                                // (Optional) Try matching device by IP if needed
                                val matchedDevice = ViewModels.receiveScreenViewModel.peerDevices.find {
                                    it.deviceAddress == info.groupOwnerAddress.hostAddress
                                }
                                ViewModels.receiveScreenViewModel.connectedToDevice.value = matchedDevice

                            }
                        }
                    }
                } else {
                    // Connection lost
                    Log.d("WiFiDirect", "Disconnected from P2P group.")
                    ViewModels.senderScreenViewModel.isConnected.value = false
                    ViewModels.receiveScreenViewModel.isConnected.value = false
                }
            }


            WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION -> {
                val device = intent.getParcelableExtra<WifiP2pDevice>(WifiP2pManager.EXTRA_WIFI_P2P_DEVICE)
                Log.d("WiFiDirect", "This device changed: ${device?.deviceName}")
            }
        }
    }


    val connectionInfoListener = WifiP2pManager.ConnectionInfoListener { info ->
        if (info.groupFormed && info.isGroupOwner) {
            // ✅ This device is the server
        } else if (info.groupFormed) {
            // ✅ This device is the client
            val hostAddress = info.groupOwnerAddress.hostAddress
            // Use this IP to connect to the server socket
        }
    }

}
