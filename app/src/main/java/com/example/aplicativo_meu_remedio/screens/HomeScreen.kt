package com.example.aplicativo_meu_remedio.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplicativo_meu_remedio.model.Medicine

@Composable
fun HomeScreen() {
    val days = listOf("DOM", "SEG", "TER", "QUA", "QUI", "SEX", "SÁB")
    var selectedDay by remember { mutableStateOf("QUA") }

    val medicines = listOf(
        Medicine("Losartana", "50 mg", "08:00", 30),
        Medicine("Omeprazol", "20 mg", "12:00", 14)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(20.dp)
    ) {
        Text("Bom dia, Marco!", fontSize = 28.sp, color = MaterialTheme.colorScheme.primary)
        Text("Remédios de hoje", fontSize = 18.sp)

        Spacer(Modifier.height(20.dp))

        LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            items(days) { day ->
                Button(
                    onClick = { selectedDay = day },
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedDay == day)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.surface
                    )
                ) {
                    Text(day)
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        medicines.forEach { medicine ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 14.dp),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(Modifier.padding(18.dp)) {
                    Text(medicine.name, fontSize = 22.sp, color = MaterialTheme.colorScheme.primary)
                    Text("Dosagem: ${medicine.dosage}", fontSize = 16.sp)
                    Text("Horário: ${medicine.time}", fontSize = 16.sp)
                    Text("Restante: ${medicine.remaining} comprimidos", fontSize = 16.sp)

                    Spacer(Modifier.height(12.dp))

                    Button(
                        onClick = {},
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Tomar", fontSize = 18.sp)
                    }
                }
            }
        }
    }
}