package br.luizfilipe.orgs.data.database.repository

import br.luizfilipe.orgs.data.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun salva(user: User)

    suspend fun remove(user: User)

    suspend fun autentica(username: String, senha: String): User?

    fun buscaPorId(id: Long): Flow<User>

    fun buscaTodos(): Flow<List<User>>

    suspend fun buscaUserPorEmail(emailUser: String): Boolean
}