package com.example.aplicativo_meu_remedio.model

data class Medicine(
    val name: String,
    val dosage: String,
    val time: String,
    val remaining: Int,
    val days: List<String> = emptyList(),
    val frequency: String = "",
    val quantityPerDose: String = "",
    val notes: String = "",
    val reminderEnabled: Boolean = true
)