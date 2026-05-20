package com.example.aplicativo_meu_remedio.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplicativo_meu_remedio.model.Medicine
import java.time.LocalDate
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState

@Composable
fun HomeScreen() {
    val days = listOf("DOM", "SEG", "TER", "QUA", "QUI", "SEX", "SÁB")
    val todayIndex = LocalDate.now().dayOfWeek.value % 7

    var selectedDay by remember { mutableStateOf(days[todayIndex]) }
    var takenMedicines by remember { mutableStateOf(setOf<String>()) }

    val medicines = listOf(
        Medicine("Losartana", "50 mg", "08:00", 30),
        Medicine("Omeprazol", "20 mg", "12:00", 14),
        Medicine("Vitamina D", "1 cápsula", "18:00", 20)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
            .padding(20.dp)
    ) {
        Text(
            text = "Olá, Marco!",
            fontSize = 30.sp,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "Confira seus medicamentos do dia.",
            fontSize = 17.sp
        )

        Spacer(modifier = Modifier.height(22.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            days.forEachIndexed { index, day ->
                val selected = selectedDay == day
                val isToday = index == todayIndex

                Button(
                    onClick = { selectedDay = day },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    contentPadding = PaddingValues(0.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selected)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.surface,
                        contentColor = if (selected)
                            MaterialTheme.colorScheme.onPrimary
                        else
                            MaterialTheme.colorScheme.primary
                    )
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(day, fontSize = 13.sp)
                        if (isToday) {
                            Text("Hoje", fontSize = 10.sp)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(26.dp))

        Text(
            text = if (selectedDay == days[todayIndex])
                "Remédios de hoje"
            else
                "Remédios de $selectedDay",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "${medicines.size} medicamentos programados",
            fontSize = 15.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        medicines.forEach { medicine ->
            val isTaken = takenMedicines.contains(medicine.name)

            MedicineHomeCard(
                medicine = medicine,
                isTaken = isTaken,
                onTakeClick = {
                    takenMedicines = if (isTaken) {
                        takenMedicines - medicine.name
                    } else {
                        takenMedicines + medicine.name
                    }
                }
            )
        }
    }
}

@Composable
fun MedicineHomeCard(
    medicine: Medicine,
    isTaken: Boolean,
    onTakeClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 14.dp),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.background),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Medication,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(32.dp)
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = medicine.name,
                        fontSize = 23.sp,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Text(
                        text = medicine.dosage,
                        fontSize = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.background,
                        shape = RoundedCornerShape(18.dp)
                    )
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Schedule,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Text(
                        text = "Horário",
                        fontSize = 13.sp
                    )
                    Text(
                        text = medicine.time,
                        fontSize = 22.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Restante",
                        fontSize = 13.sp
                    )
                    Text(
                        text = "${medicine.remaining}",
                        fontSize = 22.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Button(
                onClick = onTakeClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isTaken)
                        MaterialTheme.colorScheme.secondary
                    else
                        MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = if (isTaken) "Tomado!" else "Marcar como tomado",
                    fontSize = 18.sp
                )
            }
        }
    }
}