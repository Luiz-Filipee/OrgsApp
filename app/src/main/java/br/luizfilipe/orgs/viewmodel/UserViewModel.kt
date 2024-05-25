package br.luizfilipe.orgs.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.luizfilipe.orgs.data.database.repository.UserRepository
import br.luizfilipe.orgs.data.database.repository.UserRepositoryImp
import br.luizfilipe.orgs.data.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class UserViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _autenticacaoStatus = MutableLiveData<User?>()
    val autenticacaoStatus: LiveData<User?> = _autenticacaoStatus

    suspend fun cadastrar(user: User) = viewModelScope.launch {
        userRepository.salva(user)
    }


    suspend fun buscaUserPorEmail(emailUser: String): Boolean {
        return userRepository.buscaUserPorEmail(emailUser)
    }

    suspend fun autentica(emailUser: String, senhaUser: String) = viewModelScope.launch {
        val user = userRepository.autentica(emailUser, senhaUser)
        _autenticacaoStatus.postValue(user)
    }

    fun buscaPorId(usuarioId: Long): Flow<User> {
        return userRepository.buscaPorId(usuarioId)
    }
}