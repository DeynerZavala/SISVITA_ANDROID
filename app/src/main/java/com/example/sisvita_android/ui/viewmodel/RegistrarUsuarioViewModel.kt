package com.example.sisvita_android.ui.viewmodel
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sisvita_android.data.model.RegistrarUsuarioResponse
import com.example.sisvita_android.data.respository.UserRepository

class RegistrarUsuarioViewModel : ViewModel() {

    private val userRepository = UserRepository()

    private val _nombre = MutableLiveData("")
    val nombre: LiveData<String> get() = _nombre

    private val _apellidoPaterno = MutableLiveData("")
    val apellidoPaterno: LiveData<String> get() = _apellidoPaterno

    private val _apellidoMaterno = MutableLiveData("")
    val apellidoMaterno: LiveData<String> get() = _apellidoMaterno

    private val _correo = MutableLiveData("")
    val correo: LiveData<String> get() = _correo

    private val _contrasena = MutableLiveData("")
    val contrasena: LiveData<String> get() = _contrasena

    private val _confirmarContrasena = MutableLiveData("")
    val confirmarContrasena: LiveData<String> get() = _confirmarContrasena

    private val _correoValido = MutableLiveData(true)
    val correoValido: LiveData<Boolean> get() = _correoValido

    private val _registrarResult = MutableLiveData<RegistrarUsuarioResponse?>()
    val registrarResult: LiveData<RegistrarUsuarioResponse?> get() = _registrarResult

    private val _mensajeResult = MutableLiveData("")
    val mensajeResult: LiveData<String> get()  = _mensajeResult

    private val _contrasenaValido = MutableLiveData(false)
    val contrasenaValido: LiveData<Boolean> get() =_contrasenaValido

    private val _registroValido = MutableLiveData(false)
    val registroValido : LiveData<Boolean> get() = _registroValido

    fun onRegistroValidoChange(){
        _registroValido.value = false
    }
    fun onNombreChange(newNombre: String) {
        _nombre.value = newNombre
    }

    fun onApellidoPaternoChange(newApellidoPaterno: String) {
        _apellidoPaterno.value = newApellidoPaterno
    }

    fun onApellidoMaternoChange(newApellidoMaterno: String) {
        _apellidoMaterno.value = newApellidoMaterno
    }

    fun onCorreoChange(newCorreo: String) {
        _correo.value = newCorreo
        _correoValido.value = Patterns.EMAIL_ADDRESS.matcher(newCorreo).matches()
    }

    fun onContrasenaChange(newContrasena: String) {
        _contrasena.value = newContrasena
    }

    fun onConfirmarContrasenaChange(newConfirmarContrasena: String) {
        _confirmarContrasena.value = newConfirmarContrasena
        _contrasenaValido.value = _contrasena.value == _confirmarContrasena.value
    }

    fun registrarUsuario() {
        val nombre = _nombre.value ?: return
        val apellidoPaterno = _apellidoPaterno.value ?: return
        val apellidoMaterno = _apellidoMaterno.value ?: return
        val correo = _correo.value ?: return
        val contrasena = _contrasena.value ?: return


        if(_correoValido.value==false){
            _mensajeResult.postValue("Correo invalido")
        }
        else if(_contrasenaValido.value == false){
            _mensajeResult.postValue("Contrasenas no conciden")
        }
        else{
            userRepository.registrarUsuario(
                nombre = nombre, apellidoPaterno = apellidoPaterno, apellidoMaterno = apellidoMaterno,
                correo = correo, contrasena =contrasena
            ) { response ->
                _registrarResult.postValue(response)
                if(response?.message == "Nuevo Usuario creado"){
                    _registroValido.postValue(true)
                    _mensajeResult.postValue("")
                }
                else if (response?.message == "Datos incompletos"){
                    _registroValido.postValue(false)
                    _mensajeResult.postValue("Complete todos los campos")
                }
                else if(response?.message == "Correo electrónico ya registrado"){
                    _registroValido.postValue(false)
                    _mensajeResult.postValue("Correo electrónico ya registrado")
                }
                else {
                    _registroValido.postValue(false)
                    _mensajeResult.postValue("Error al crear el usuario")
                }
            }
        }
    }

}
