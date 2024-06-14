package com.example.sisvita_android.utils

import com.example.sisvita_android.data.model.Usuario

object UserManager {
    private var usuario: Usuario? = null

    fun setUser(user: Usuario) {
        usuario = user
    }

    fun getUser(): Usuario? {
        return usuario
    }

    fun clearUser() {
        usuario = null
    }
}

