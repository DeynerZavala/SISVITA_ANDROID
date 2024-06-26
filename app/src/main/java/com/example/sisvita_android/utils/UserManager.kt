package com.example.sisvita_android.utils

import com.example.sisvita_android.data.model.Usuario
import com.example.sisvita_android.ui.viewmodel.LoginViewModel

object UserManager {
    private var usuario: Usuario? = null
//        Usuario(usuario_id = 4,
//        "Cristhian","2024-06-21T07:50:18.778554",
//        "cristhian@gmail.com",
//        "Retuerto", "Contreras")
    private var rol: String?= null //"Especialista"
    private var activo: Boolean?= false //true

    fun setUser(user: Usuario, rol:String, activo:Boolean) {
        this.usuario = user
        this.rol = rol
        this.activo = activo
    }

    fun getUser(): Usuario? {
        return usuario
    }

    fun getRol(): String? {
        return rol
    }

    fun getActivo(): Boolean? {
        return activo
    }
    fun clearUser() {
        usuario = null
        rol = null
        activo = false
    }
}

