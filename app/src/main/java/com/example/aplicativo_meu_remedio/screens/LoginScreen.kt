package com.example.aplicativo_meu_remedio.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplicativo_meu_remedio.R
import com.example.aplicativo_meu_remedio.repository.AuthRepository

@Composable
fun LoginScreen(
    onLoginClick: () -> Unit
) {
    var isRegisterMode by remember { mutableStateOf(false) }

    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(8.dp, RoundedCornerShape(24.dp)),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_meu_remedio),
                    contentDescription = "Logo Meu Remédio",
                    modifier = Modifier.size(160.dp)
                )

                Text(
                    text = if (isRegisterMode) "Criar conta" else "Bem-vindo!",
                    fontSize = 28.sp,
                    color = MaterialTheme.colorScheme.primary
                )

                if (errorMessage.isNotBlank()) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.background
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = errorMessage,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }

                if (isRegisterMode) {
                    OutlinedTextField(
                        value = nome,
                        onValueChange = { nome = it },
                        label = { Text("Nome") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("E-mail") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = senha,
                    onValueChange = { senha = it },
                    label = { Text("Senha") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true
                )

                Button(
                    onClick = {
                        errorMessage = ""

                        if (isRegisterMode) {
                            if (nome.isBlank() || email.isBlank() || senha.isBlank()) {
                                errorMessage = "Preencha nome, e-mail e senha."
                                return@Button
                            }

                            if (senha.length < 6) {
                                errorMessage = "A senha precisa ter pelo menos 6 caracteres."
                                return@Button
                            }

                            val registered = AuthRepository.register(
                                name = nome,
                                email = email,
                                password = senha
                            )

                            if (registered) {
                                onLoginClick()
                            } else {
                                errorMessage = "Este e-mail já está cadastrado."
                            }
                        } else {
                            if (email.isBlank() || senha.isBlank()) {
                                errorMessage = "Preencha e-mail e senha."
                                return@Button
                            }

                            val logged = AuthRepository.login(
                                email = email,
                                password = senha
                            )

                            if (logged) {
                                onLoginClick()
                            } else {
                                errorMessage = "E-mail ou senha inválidos."
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),
                    shape = RoundedCornerShape(18.dp)
                ) {
                    Text(
                        text = if (isRegisterMode) "Cadastrar" else "Entrar",
                        fontSize = 18.sp
                    )
                }

                TextButton(
                    onClick = {
                        isRegisterMode = !isRegisterMode
                        errorMessage = ""
                    }
                ) {
                    Text(
                        text = if (isRegisterMode)
                            "Já tenho conta"
                        else
                            "Criar conta"
                    )
                }
            }
        }
    }
}