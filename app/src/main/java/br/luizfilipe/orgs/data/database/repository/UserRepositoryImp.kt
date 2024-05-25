package br.luizfilipe.orgs.data.database.repository

import android.util.Log
import br.luizfilipe.orgs.data.database.dao.LocalUserDataSource
import br.luizfilipe.orgs.data.model.User
import kotlinx.coroutines.flow.Flow


class UserRepositoryImp(
    private val localUserDataSource: LocalUserDataSource
) : UserRepository {

    companion object {
        private const val TAG = "UserRepository"
    }

    override suspend fun salva(user: User) {

        try {
            localUserDataSource.salva(user)
            Log.i(TAG, "salva: usuario salvo com sucesso $user")
        } catch (e: Exception) {
            Log.e(TAG, "salva: falha ao salvar usuario", e)
            throw e // Rethrow the exception to be handled upstream
        }
    }

    override suspend fun remove(user: User) {
        try {

            localUserDataSource.remove(user)

        } catch (e: Exception) {

            Log.e(TAG, "salva: falha ao salvar usuario", e)

        }
    }

    override suspend fun autentica(username: String, senha: String): User? {
        return localUserDataSource.autentica(username, senha)
    }

    override fun buscaPorId(id: Long): Flow<User> {
        return localUserDataSource.buscaPorId(id)
    }

    override fun buscaTodos(): Flow<List<User>> {
        return localUserDataSource.buscaTodos()
    }

    override suspend fun buscaUserPorEmail(emailUser: String): Boolean {
        return localUserDataSource.buscaUserPorEmail(emailUser) != null
    }


}