package com.sid.dimensio

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.sid.designsystem.AppTheme
import com.sid.dimensio.navigation.AppNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainApp()
        }
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun MainApp() {
    AppTheme {
        val navController = rememberNavController()
        AppNavHost(
            navController = navController
        )
    }

    //Enable edge to edge
    val context = LocalActivity.current as ComponentActivity
    context.enableEdgeToEdge()


}

