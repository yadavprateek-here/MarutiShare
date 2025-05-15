package com.rudra.marutishare.UIScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rudra.marutishare.Nav
import com.rudra.marutishare.ViewModels
import com.rudra.marutishare.WiFiP2PContext
import com.rudra.marutishare.viewModel.SenderScreenViewModel
import kotlinx.coroutines.coroutineScope

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MarutiShareApp() {
    Nav.nav = rememberNavController()

    NavHost(Nav.nav, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onSendClick = {Nav.nav.navigate("send") },
                onReceiveClick = { Nav.nav.navigate("receive") }
            )
        }
        composable("send") {
            ViewModels.senderScreenViewModel = viewModel()
            SendScreen(ViewModels.senderScreenViewModel)
        }
        composable("receive") {
            ReceiveScreen(ViewModels.receiveScreenViewModel)
        }

        composable("receiveFiles"){
            ReceiveFiles()
        }
        composable("FileReceivingNetworkPage") {
            ReceivingScreen()
        }

    }
}


