package com.example.aplicativo_meu_remedio.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplicativo_meu_remedio.repository.MedicineRepository
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun AgendaScreen() {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var selectedView by remember { mutableStateOf("Mês") }

    val dayName = getDayShortName(selectedDate)
    val medicinesOfDay = MedicineRepository.medicines.filter {
        it.days.contains(dayName) || it.days.isEmpty()
    }

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
                modifier = Modifier.padding(22.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.CalendarMonth,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(46.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "Agenda",
                    fontSize = 32.sp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("Dia", "Semana", "Mês").forEach { item ->
                Button(
                    onClick = { selectedView = item },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedView == item)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.surface,
                        contentColor = if (selectedView == item)
                            MaterialTheme.colorScheme.onPrimary
                        else
                            MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(item, fontSize = 15.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        when (selectedView) {
            "Dia" -> DayCard(selectedDate)
            "Semana" -> WeekSelector(
                selectedDate = selectedDate,
                onDateSelected = { selectedDate = it }
            )
            "Mês" -> MonthCalendar(
                selectedDate = selectedDate,
                onDateSelected = { selectedDate = it }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Remédios do dia",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "${selectedDate.dayOfMonth} de ${
                selectedDate.month.getDisplayName(
                    TextStyle.FULL,
                    Locale("pt", "BR")
                )
            }",
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(14.dp))

        if (medicinesOfDay.isEmpty()) {
            EmptyAgendaCard()
        } else {
            medicinesOfDay.forEach { medicine ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.background),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Medication,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = medicine.name,
                                fontSize = 21.sp,
                                color = MaterialTheme.colorScheme.primary
                            )

                            Text(
                                text = medicine.dosage,
                                fontSize = 15.sp
                            )
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Icon(
                                imageVector = Icons.Default.Schedule,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )

                            Text(
                                text = medicine.time,
                                fontSize = 18.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(90.dp))
    }
}

@Composable
fun DayCard(selectedDate: LocalDate) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(22.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = getDayShortName(selectedDate),
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = selectedDate.dayOfMonth.toString(),
                fontSize = 52.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun WeekSelector(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    val startOfWeek = selectedDate.minusDays((selectedDate.dayOfWeek.value % 7).toLong())

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        for (i in 0..6) {
            val date = startOfWeek.plusDays(i.toLong())
            val selected = date == selectedDate

            Button(
                onClick = { onDateSelected(date) },
                modifier = Modifier
                    .weight(1f)
                    .height(64.dp),
                contentPadding = PaddingValues(0.dp),
                shape = RoundedCornerShape(16.dp),
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
                    Text(getDayShortName(date), fontSize = 12.sp)
                    Text(date.dayOfMonth.toString(), fontSize = 18.sp)
                }
            }
        }
    }
}

@Composable
fun MonthCalendar(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    val yearMonth = YearMonth.from(selectedDate)
    val daysInMonth = yearMonth.lengthOfMonth()
    val firstDay = yearMonth.atDay(1)
    val emptyDays = firstDay.dayOfWeek.value % 7

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = yearMonth.month.getDisplayName(TextStyle.FULL, Locale("pt", "BR"))
                    .replaceFirstChar { it.uppercase() } + " ${yearMonth.year}",
                fontSize = 22.sp,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                listOf("D", "S", "T", "Q", "Q", "S", "S").forEach {
                    Text(
                        text = it,
                        modifier = Modifier.weight(1f),
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            val totalCells = emptyDays + daysInMonth
            val rows = (totalCells + 6) / 7

            for (row in 0 until rows) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    for (col in 0..6) {
                        val cellIndex = row * 7 + col
                        val dayNumber = cellIndex - emptyDays + 1

                        if (dayNumber in 1..daysInMonth) {
                            val date = yearMonth.atDay(dayNumber)
                            val selected = date == selectedDate

                            Button(
                                onClick = { onDateSelected(date) },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(44.dp),
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
                                Text(dayNumber.toString(), fontSize = 14.sp)
                            }
                        } else {
                            Spacer(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(44.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))
            }
        }
    }
}

@Composable
fun EmptyAgendaCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(22.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Nenhum remédio nesse dia",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Você pode cadastrar um novo remédio na aba Adicionar.",
                fontSize = 15.sp
            )
        }
    }
}

fun getDayShortName(date: LocalDate): String {
    val days = listOf("DOM", "SEG", "TER", "QUA", "QUI", "SEX", "SÁB")
    return days[date.dayOfWeek.value % 7]
}