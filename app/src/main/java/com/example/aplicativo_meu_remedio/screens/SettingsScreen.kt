package com.example.aplicativo_meu_remedio.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Accessibility
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsScreen(
    onLogoutClick: () -> Unit
) {
    var notificacoesAtivas by remember { mutableStateOf(true) }
    var modoIdoso by remember { mutableStateOf(false) }
    var horarioLembrete by remember { mutableStateOf("10 minutos antes") }
    var nomeUsuario by remember { mutableStateOf("Marco") }
    var emailUsuario by remember { mutableStateOf("marco@email.com") }
    var showLogoutDialog by remember { mutableStateOf(false) }

    val titleSize = if (modoIdoso) 34.sp else 30.sp
    val textSize = if (modoIdoso) 20.sp else 16.sp
    val itemTitleSize = if (modoIdoso) 22.sp else 18.sp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
            .padding(20.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(26.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp, vertical = 26.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(50.dp)
                )

                Spacer(modifier = Modifier.width(18.dp))

                Text(
                    text = "Configurações",
                    fontSize = titleSize,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        SettingsCard(title = "Lembretes") {
            SettingSwitchItem(
                icon = Icons.Default.Notifications,
                title = "Notificações",
                description = "Receber avisos dos remédios",
                checked = notificacoesAtivas,
                titleSize = itemTitleSize,
                textSize = textSize,
                onCheckedChange = { notificacoesAtivas = it }
            )

            Spacer(modifier = Modifier.height(14.dp))

            OutlinedTextField(
                value = horarioLembrete,
                onValueChange = { horarioLembrete = it },
                label = { Text("Lembrete padrão") },
                placeholder = { Text("Ex: 10 minutos antes") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Alarm,
                        contentDescription = null
                    )
                },
                singleLine = true,
                enabled = notificacoesAtivas
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        SettingsCard(title = "Acessibilidade") {
            SettingSwitchItem(
                icon = Icons.Default.Accessibility,
                title = "Modo idoso",
                description = "Aumenta textos e facilita a leitura",
                checked = modoIdoso,
                titleSize = itemTitleSize,
                textSize = textSize,
                onCheckedChange = { modoIdoso = it }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        SettingsCard(title = "Conta") {
            OutlinedTextField(
                value = nomeUsuario,
                onValueChange = { nomeUsuario = it },
                label = { Text("Nome") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = null
                    )
                },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = emailUsuario,
                onValueChange = { emailUsuario = it },
                label = { Text("E-mail") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Button(
            onClick = { showLogoutDialog = true },
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Icon(
                imageVector = Icons.Default.ExitToApp,
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Sair da conta",
                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.height(90.dp))
    }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            icon = {
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            title = {
                Text("Deseja sair?")
            },
            text = {
                Text("Você voltará para a tela de login.")
            },
            confirmButton = {
                Button(
                    onClick = {
                        showLogoutDialog = false
                        onLogoutClick()
                    }
                ) {
                    Text("Sair")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showLogoutDialog = false }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun SettingsCard(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Text(
                text = title,
                fontSize = 22.sp,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(14.dp))

            content()
        }
    }
}

@Composable
fun SettingSwitchItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    description: String,
    checked: Boolean,
    titleSize: androidx.compose.ui.unit.TextUnit,
    textSize: androidx.compose.ui.unit.TextUnit,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.background,
                RoundedCornerShape(18.dp)
            )
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(32.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = titleSize,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = description,
                fontSize = textSize
            )
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}