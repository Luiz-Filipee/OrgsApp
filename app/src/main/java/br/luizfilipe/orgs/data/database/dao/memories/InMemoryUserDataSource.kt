package br.luizfilipe.orgs.data.database.dao.memories

import br.luizfilipe.orgs.data.database.dao.LocalUserDataSource
import br.luizfilipe.orgs.data.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import java.util.concurrent.ConcurrentHashMap

class InMemoryUserDataSource : LocalUserDataSource {

    private val users = ConcurrentHashMap<Long, User>()
    private val usersFlow = MutableStateFlow<List<User>>(emptyList())

    override fun buscaTodos(): Flow<List<User>> = usersFlow

    override fun buscaPorId(idUser: Long): Flow<User> = flow {
        users[idUser]?.let { emit(it) }
    }

    override suspend fun autentica(username: String, senha: String): User? {
        return users.values.find { it.email == username && it.senha == senha }
    }

    override suspend fun buscaUserPorEmail(emailUser: String): User? {
        return users.values.find { it.email == emailUser }
    }

    override suspend fun salva(o: User) {
        users[o.id] = o
        usersFlow.update { users.values.toList() }
    }

    override suspend fun remove(o: User) {
        users.remove(o.id)
    }

    override suspend fun update(o: User) {
        users.remove(o.id)
        usersFlow.update { users.values.toList() }
    }

}