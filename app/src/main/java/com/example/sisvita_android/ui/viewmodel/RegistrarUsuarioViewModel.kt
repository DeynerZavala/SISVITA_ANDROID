package com.example.sisvita_android.ui.viewmodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sisvita_android.data.model.RegistrarUsuarioResponse
import com.example.sisvita_android.data.model.TituloData
import com.example.sisvita_android.data.respository.EspecialistaRepository
import com.example.sisvita_android.data.respository.UbigeoRespository
import com.example.sisvita_android.data.respository.UserRepository

class RegistrarUsuarioViewModel : ViewModel() {

    private val userRepository = UserRepository()
    private val especialistaRespository = EspecialistaRepository()

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

    private val _ubigeo = MutableLiveData("")
    val ubigeo: LiveData<String> get() = _ubigeo

    private val _registrarResult = MutableLiveData<RegistrarUsuarioResponse?>()
    val registrarResult: LiveData<RegistrarUsuarioResponse?> get() = _registrarResult

    private val _mensajeResult = MutableLiveData("")
    val mensajeResult: LiveData<String> get() = _mensajeResult

    private val _contrasenaValido = MutableLiveData(false)
    val contrasenaValido: LiveData<Boolean> get() = _contrasenaValido

    private val _registroValido = MutableLiveData(false)
    val registroValido: LiveData<Boolean> get() = _registroValido

    private val _roles = listOf("Estudiante", "Especialista")
    val roles: List<String> get() = _roles

    private val _selectedRole = MutableLiveData(_roles[0])
    val selectedRole: LiveData<String> get() = _selectedRole

    private val _dropdownItems = MutableLiveData<List<TituloData>>(emptyList())
    val dropdownItems: LiveData<List<TituloData>> get() = _dropdownItems

    private val _selectedDropdownItem = MutableLiveData<TituloData?>()
    val selectedDropdownItem: LiveData<TituloData?> get() = _selectedDropdownItem


    private val ubigeoRepository = UbigeoRespository()

    private val _departamento = MutableLiveData("")
    val departamento: LiveData<String> get() = _departamento

    private val _provincia = MutableLiveData("")
    val provincia: LiveData<String> get() = _provincia

    private val _distrito = MutableLiveData("")
    val distrito: LiveData<String> get() = _distrito

    private val _departamentos = MutableLiveData<List<String>>()
    val departamentos: LiveData<List<String>> get() = _departamentos

    private val _provincias = MutableLiveData<List<String>>()
    val provincias: LiveData<List<String>> get() = _provincias

    private val _distritos = MutableLiveData<List<String>>()
    val distritos: LiveData<List<String>> get() = _distritos

    private val _departamentoMenuExpanded = MutableLiveData(false)
    val departamentoMenuExpanded: LiveData<Boolean> get() = _departamentoMenuExpanded

    private val _provinciaMenuExpanded = MutableLiveData(false)
    val provinciaMenuExpanded: LiveData<Boolean> get() = _provinciaMenuExpanded

    private val _distritoMenuExpanded = MutableLiveData(false)
    val distritoMenuExpanded: LiveData<Boolean> get() = _distritoMenuExpanded


    init {
        fetchDepartamentos()
    }

    fun fetchDepartamentos() {
        ubigeoRepository.getDepartamentos { response ->
            response?.data?.let { data ->
                _departamentos.postValue(data)
            }
        }
    }


fun fetchProvincias(departamento: String) {
    ubigeoRepository.getProvincias(departamento) { response ->
        response?.data?.let { data ->
            _provincias.postValue(data)
        }
    }
}

fun fetchDistritos(provincia: String) {
    ubigeoRepository.getDistritos(provincia) { response ->
        response?.data?.let { data ->
            _distritos.postValue(data)
        }
    }
}
    fun onDepartamentoChange(newDepartamento: String) {
        _departamento.value = newDepartamento
        _provincia.value = ""
        _distrito.value = ""
        _provincias.value = emptyList()
        _distritos.value = emptyList()
        fetchProvincias(newDepartamento)
    }

    fun onProvinciaChange(newProvincia: String) {
        _provincia.value = newProvincia
        _distrito.value = ""
        _distritos.value = emptyList()
        fetchDistritos(newProvincia)
    }

    fun onDistritoChange(newDistrito: String) {
        _distrito.value = newDistrito
    }


    fun onDepartamentoMenuExpandedChange() {
        _departamentoMenuExpanded.value = _departamentoMenuExpanded.value != true
    }

    fun onProvinciaMenuExpandedChange() {
        _provinciaMenuExpanded.value = _provinciaMenuExpanded.value != true
    }

    fun onDistritoMenuExpandedChange() {
        _distritoMenuExpanded.value = _distritoMenuExpanded.value != true
    }

    fun iniciarTitulo() {
        especialistaRespository.getTitulos { response ->
            response?.data?.let { item ->
                _dropdownItems.postValue(item)
            }
        }
    }

    fun onSelectedDropdownItemChange(newItem: TituloData) {
        _selectedDropdownItem.value = newItem
    }

    fun onSelectedRoleChange(newRole: String) {
        _selectedRole.value = newRole
    }

    fun onRegistroValidoChange() {
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

    fun onUbigeoChange(newUbigeo: String) {
        _ubigeo.value = newUbigeo
    }

    fun registrarUsuario() {
        val nombre = _nombre.value ?: ""
        val apellidoPaterno = _apellidoPaterno.value ?: ""
        val apellidoMaterno = _apellidoMaterno.value ?: ""
        val correo = _correo.value ?: ""
        val contrasena = _contrasena.value ?: ""
        val confirmarContrasena = _confirmarContrasena.value ?: ""
        val departamento = _departamento.value ?: ""
        val provincia = _provincia.value ?: ""
        val distrito = _distrito.value ?: ""

        if (nombre.isEmpty() || apellidoPaterno.isEmpty() || apellidoMaterno.isEmpty() ||
            correo.isEmpty() || contrasena.isEmpty() || confirmarContrasena.isEmpty() ||
            departamento.isEmpty() || provincia.isEmpty() || distrito.isEmpty()) {
            _registroValido.postValue(false)
            _mensajeResult.postValue("Complete todos los campos")
            return
        }

        if (_correoValido.value == false) {
            _mensajeResult.postValue("Correo invalido")
        } else if (_contrasenaValido.value == false) {
            _mensajeResult.postValue("Contrasenas no conciden")
        } else {
            if (_selectedRole.value == "Estudiante") {
                userRepository.registrarUsuario(
                    nombre = nombre, apellidoPaterno = apellidoPaterno, apellidoMaterno = apellidoMaterno,
                    correo = correo, contrasena = contrasena, departamento = departamento,
                    provincia = provincia, distrito = distrito
                ) { response ->
                    _registrarResult.postValue(response)
                    if (response?.message == "Nuevo Usuario creado") {
                        _registroValido.postValue(true)
                        _mensajeResult.postValue("")
                    } else {
                        _registroValido.postValue(false)
                        _mensajeResult.postValue(response?.message ?: "Error al crear el usuario")
                    }
                }
            } else if (_selectedRole.value == "Especialista") {
                if (_selectedDropdownItem.value == null) {
                    _registroValido.postValue(false)
                    _mensajeResult.postValue("Escoge un titulo")
                    return
                }
                especialistaRespository.registrarEspecialista(
                    nombre = nombre, apellidoPaterno = apellidoPaterno, apellidoMaterno = apellidoMaterno,
                    correo = correo, contrasena = contrasena, departamento = departamento,
                    provincia = provincia, distrito = distrito,
                    titulo_id = _selectedDropdownItem.value!!.titulo_id
                ) { response ->
                    _registrarResult.postValue(response)
                    if (response?.message == "Nuevo Especialista creado") {
                        _registroValido.postValue(true)
                        _mensajeResult.postValue("")
                    } else {
                        _registroValido.postValue(false)
                        _mensajeResult.postValue(response?.message ?: "Error al crear el especialista")
                    }
                }
            }
        }
    }

}
