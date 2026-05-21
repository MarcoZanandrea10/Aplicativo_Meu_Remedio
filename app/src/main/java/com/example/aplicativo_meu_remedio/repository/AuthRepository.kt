package com.example.aplicativo_meu_remedio.repository

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.aplicativo_meu_remedio.model.User

object AuthRepository {
    private val users = mutableStateListOf(
        User(
            name = "Marco",
            email = "marco@email.com",
            password = "123456"
        )
    )

    var currentUser = mutableStateOf<User?>(null)
        private set

    fun login(email: String, password: String): Boolean {
        val user = users.find {
            it.email == email && it.password == password
        }

        currentUser.value = user
        return user != null
    }

    fun register(name: String, email: String, password: String): Boolean {
        val emailAlreadyExists = users.any { it.email == email }

        if (emailAlreadyExists) {
            return false
        }

        val newUser = User(
            name = name,
            email = email,
            password = password
        )

        users.add(newUser)
        currentUser.value = newUser

        return true
    }

    fun logout() {
        currentUser.value = null
    }

    fun getUserName(): String {
        return currentUser.value?.name ?: "Usuário"
    }
}