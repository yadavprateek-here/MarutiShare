package com.rudra.marutishare


import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.net.wifi.p2p.WifiP2pDevice

import android.net.wifi.p2p.WifiP2pManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import com.rudra.marutishare.UIScreen.MarutiShareApp
import com.rudra.marutishare.Utils.DeleteGroup
import com.rudra.marutishare.Utils.WiFiDirectBroadcastReceiver
import com.rudra.marutishare.ui.theme.MarutiShareTheme
import com.rudra.marutishare.viewModel.ReceiveScreenViewModel
import com.rudra.marutishare.viewModel.SenderScreenViewModel
import java.io.File


class MainActivity : ComponentActivity() {

    lateinit var receiver: BroadcastReceiver
    val intentFilter = IntentFilter().apply {
        addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)
        addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)
        addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION)
        addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ViewModels.senderScreenViewModel = ViewModelProvider(this).get(SenderScreenViewModel::class.java)
        ViewModels.receiveScreenViewModel = ViewModelProvider(this).get(ReceiveScreenViewModel::class.java)




        WiFiP2PContext.manager = getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager
        WiFiP2PContext.channel = WiFiP2PContext.manager.initialize(this, mainLooper, null)
        WiFiP2PContext.peerList = emptyList<WifiP2pDevice>()
        WiFiP2PContext.context = this



//        enableEdgeToEdge()
        setContent {
            MarutiShareTheme {
                MarutiShareApp()
            }
        }

        receiver = WiFiDirectBroadcastReceiver(WiFiP2PContext.manager, WiFiP2PContext.channel, this)
        registerReceiver(receiver, intentFilter)





    }

    public override fun onResume() {
        super.onResume()
    }

    public override fun onPause() {
        super.onPause()

    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            DeleteGroup()
            unregisterReceiver(receiver)
        } catch (e: IllegalArgumentException) {
            // Receiver was not registered or already unregistered
        }
    }

    fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it,"Share5Media").apply { mkdirs() } }
        return if ((mediaDir != null) && mediaDir.exists()) mediaDir else filesDir
    }


}

object WiFiP2PContext {
    lateinit var manager: WifiP2pManager
    lateinit var channel: WifiP2pManager.Channel
    lateinit var peerList: Collection<WifiP2pDevice>
    lateinit var context : MainActivity
    var ConnectedToDevice: WifiP2pDevice? = null

}

object ViewModels{
    lateinit var receiveScreenViewModel: ReceiveScreenViewModel
    lateinit var senderScreenViewModel: SenderScreenViewModel
}

@SuppressLint("StaticFieldLeak")
object Nav{
    lateinit var nav: NavHostController
}

