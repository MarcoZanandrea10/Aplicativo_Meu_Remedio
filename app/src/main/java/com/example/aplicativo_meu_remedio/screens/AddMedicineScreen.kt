package com.example.aplicativo_meu_remedio.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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

    val dias = listOf("DOM", "SEG", "TER", "QUA", "QUI", "SEX", "SÁB")
    var diasSelecionados by remember { mutableStateOf(setOf<String>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
            .padding(20.dp)
    ) {
        Text(
            text = "Novo Remédio",
            fontSize = 30.sp,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "Cadastre as informações do medicamento.",
            fontSize = 16.sp
        )

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

                OutlinedTextField(
                    value = nome,
                    onValueChange = { nome = it },
                    label = { Text("Nome do remédio") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = dosagem,
                    onValueChange = { dosagem = it },
                    label = { Text("Dosagem") },
                    placeholder = { Text("Ex: 50 mg") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
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
                    label = { Text("Horário") },
                    placeholder = { Text("Ex: 08:00") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Text(
                    text = "Dias da semana",
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

                OutlinedTextField(
                    value = quantidadeTotal,
                    onValueChange = { quantidadeTotal = it },
                    label = { Text("Quantidade total") },
                    placeholder = { Text("Ex: 30") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = quantidadeDose,
                    onValueChange = { quantidadeDose = it },
                    label = { Text("Quantidade por dose") },
                    placeholder = { Text("Ex: 1 comprimido") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
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
                    onClick = {},
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
}