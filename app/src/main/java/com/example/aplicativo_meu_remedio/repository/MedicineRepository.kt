package com.example.aplicativo_meu_remedio.repository

import androidx.compose.runtime.mutableStateListOf
import com.example.aplicativo_meu_remedio.model.Medicine

object MedicineRepository {
    val medicines = mutableStateListOf<Medicine>()

    fun addMedicine(medicine: Medicine) {
        medicines.add(medicine)
    }
}