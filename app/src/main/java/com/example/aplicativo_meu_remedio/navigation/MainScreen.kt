package com.example.aplicativo_meu_remedio.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.aplicativo_meu_remedio.components.BottomNavigationBar
import com.example.aplicativo_meu_remedio.screens.AddMedicineScreen
import com.example.aplicativo_meu_remedio.screens.AgendaScreen
import com.example.aplicativo_meu_remedio.screens.HomeScreen
import com.example.aplicativo_meu_remedio.screens.SettingsScreen

@Composable
fun MainScreen(
    onLogoutClick: () -> Unit
) {
    var selectedItem by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedItem = selectedItem,
                onItemSelected = { selectedItem = it }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            when (selectedItem) {
                0 -> HomeScreen()
                1 -> AddMedicineScreen()
                2 -> AgendaScreen()
                3 -> SettingsScreen(
                    onLogoutClick = onLogoutClick
                )
            }
        }
    }
}