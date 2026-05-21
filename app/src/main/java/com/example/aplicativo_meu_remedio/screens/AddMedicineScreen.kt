package com.example.aplicativo_meu_remedio.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.aplicativo_meu_remedio.model.Medicine
import com.example.aplicativo_meu_remedio.repository.MedicineRepository

@Composable
fun AddMedicineScreen() {
    var nome by remember { mutableStateOf("") }
    var dosagem by remember { mutableStateOf("") }
    var frequencia by remember { mutableStateOf("") }
    var horario by remember { mutableStateOf("") }
    var quantidadeTotal by remember { mutableStateOf("") }
    var quantidadeDose by remember { mutableStateOf("") }
    var observacoes by remember { mutableStateOf("") }
    var lembreteAtivo by remember { mutableStateOf(true) }

    var showSuccessDialog by remember { mutableStateOf(false) }
    var showRequiredWarning by remember { mutableStateOf(false) }

    val dias = listOf("DOM", "SEG", "TER", "QUA", "QUI", "SEX", "SÁB")
    var diasSelecionados by remember { mutableStateOf(setOf<String>()) }

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
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp, vertical = 26.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Medication,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(50.dp)
                )

                Spacer(modifier = Modifier.width(18.dp))

                Text(
                    text = "Cadastrar Remédio",
                    fontSize = 30.sp,
                    lineHeight = 34.sp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        Card(
            shape = RoundedCornerShape(26.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Medication,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(34.dp)
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = "Dados do remédio",
                        fontSize = 22.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                if (showRequiredWarning) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.background
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )

                            Spacer(modifier = Modifier.width(10.dp))

                            Text(
                                text = "Preencha os campos obrigatórios marcados com *.",
                                fontSize = 15.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = nome,
                    onValueChange = { nome = it },
                    label = { Text("Nome do remédio *") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isError = showRequiredWarning && nome.isBlank(),
                    supportingText = {
                        if (showRequiredWarning && nome.isBlank()) {
                            Text("Informe o nome do remédio")
                        }
                    }
                )

                OutlinedTextField(
                    value = dosagem,
                    onValueChange = { dosagem = it },
                    label = { Text("Dosagem *") },
                    placeholder = { Text("Ex: 50 mg") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isError = showRequiredWarning && dosagem.isBlank(),
                    supportingText = {
                        if (showRequiredWarning && dosagem.isBlank()) {
                            Text("Informe a dosagem")
                        }
                    }
                )

                OutlinedTextField(
                    value = frequencia,
                    onValueChange = { frequencia = it },
                    label = { Text("Frequência") },
                    placeholder = { Text("Ex: A cada 8 horas") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = horario,
                    onValueChange = { horario = it },
                    label = { Text("Horário *") },
                    placeholder = { Text("Ex: 08:00") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isError = showRequiredWarning && horario.isBlank(),
                    supportingText = {
                        if (showRequiredWarning && horario.isBlank()) {
                            Text("Informe o horário")
                        }
                    }
                )

                Text(
                    text = "Dias da semana *",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.primary
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    dias.forEach { dia ->
                        val selected = diasSelecionados.contains(dia)

                        Button(
                            onClick = {
                                diasSelecionados = if (selected) {
                                    diasSelecionados - dia
                                } else {
                                    diasSelecionados + dia
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(46.dp),
                            contentPadding = PaddingValues(0.dp),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (selected)
                                    MaterialTheme.colorScheme.primary
                                else
                                    MaterialTheme.colorScheme.background,
                                contentColor = if (selected)
                                    MaterialTheme.colorScheme.onPrimary
                                else
                                    MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text(dia, fontSize = 12.sp)
                        }
                    }
                }

                if (showRequiredWarning && diasSelecionados.isEmpty()) {
                    Text(
                        text = "Selecione pelo menos um dia da semana",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                OutlinedTextField(
                    value = quantidadeTotal,
                    onValueChange = { quantidadeTotal = it },
                    label = { Text("Quantidade total *") },
                    placeholder = { Text("Ex: 30") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isError = showRequiredWarning && quantidadeTotal.isBlank(),
                    supportingText = {
                        if (showRequiredWarning && quantidadeTotal.isBlank()) {
                            Text("Informe a quantidade total")
                        }
                    }
                )

                OutlinedTextField(
                    value = quantidadeDose,
                    onValueChange = { quantidadeDose = it },
                    label = { Text("Quantidade por dose *") },
                    placeholder = { Text("Ex: 1 comprimido") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isError = showRequiredWarning && quantidadeDose.isBlank(),
                    supportingText = {
                        if (showRequiredWarning && quantidadeDose.isBlank()) {
                            Text("Informe a quantidade por dose")
                        }
                    }
                )

                OutlinedTextField(
                    value = observacoes,
                    onValueChange = { observacoes = it },
                    label = { Text("Observações") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )

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
                        imageVector = Icons.Default.Alarm,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text("Ativar lembrete", fontSize = 18.sp)
                        Text("Receber notificação no horário", fontSize = 14.sp)
                    }

                    Switch(
                        checked = lembreteAtivo,
                        onCheckedChange = { lembreteAtivo = it }
                    )
                }

                Button(
                    onClick = {
                        val camposObrigatoriosPreenchidos =
                            nome.isNotBlank() &&
                                    dosagem.isNotBlank() &&
                                    horario.isNotBlank() &&
                                    quantidadeTotal.isNotBlank() &&
                                    quantidadeDose.isNotBlank() &&
                                    diasSelecionados.isNotEmpty()

                        if (!camposObrigatoriosPreenchidos) {
                            showRequiredWarning = true
                        } else {
                            MedicineRepository.addMedicine(
                                Medicine(
                                    name = nome,
                                    dosage = dosagem,
                                    time = horario,
                                    remaining = quantidadeTotal.toIntOrNull() ?: 0,
                                    days = diasSelecionados.toList(),
                                    frequency = frequencia,
                                    quantityPerDose = quantidadeDose,
                                    notes = observacoes,
                                    reminderEnabled = lembreteAtivo
                                )
                            )

                            nome = ""
                            dosagem = ""
                            frequencia = ""
                            horario = ""
                            quantidadeTotal = ""
                            quantidadeDose = ""
                            observacoes = ""
                            diasSelecionados = emptySet()
                            lembreteAtivo = true
                            showRequiredWarning = false
                            showSuccessDialog = true
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),
                    shape = RoundedCornerShape(18.dp)
                ) {
                    Icon(Icons.Default.Save, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Salvar remédio", fontSize = 18.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(90.dp))
    }

    if (showSuccessDialog) {
        Dialog(
            onDismissRequest = { showSuccessDialog = false }
        ) {
            Card(
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(26.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(68.dp)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Remédio adicionado!",
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(22.dp))

                    Button(
                        onClick = { showSuccessDialog = false },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        shape = RoundedCornerShape(18.dp)
                    ) {
                        Text("Fechar", fontSize = 18.sp)
                    }
                }
            }
        }
    }
}