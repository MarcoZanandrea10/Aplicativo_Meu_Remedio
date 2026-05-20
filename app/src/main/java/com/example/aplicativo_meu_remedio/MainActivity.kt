package com.example.aplicativo_meu_remedio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplicativo_meu_remedio.ui.theme.Aplicativo_Meu_RemedioTheme
import com.example.aplicativo_meu_remedio.screens.LoginScreen
import androidx.compose.runtime.*
import com.example.aplicativo_meu_remedio.screens.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Aplicativo_Meu_RemedioTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        var loggedIn by remember { mutableStateOf(false) }

                        if (loggedIn) {
                            HomeScreen()
                        } else {
                            LoginScreen(
                                onLoginClick = { loggedIn = true }
                            )
                        }
                    }
                }
            }
        }
    }
}