package br.luizfilipe.orgs.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import br.luizfilipe.orgs.data.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalUserDataSource : InterfaceDataSource<User> {

    @Query("SELECT * FROM user")
    fun buscaTodos(): Flow<List<User>>

    @Query("SELECT * FROM user WHERE id = :idUser")
    fun buscaPorId(idUser: Long): Flow<User>

    @Query("SELECT * FROM user WHERE email = :username AND senha = :senha")
    suspend fun autentica(username: String, senha: String): User?

    @Query("SELECT * FROM user WHERE email = :emailUser")
    suspend fun buscaUserPorEmail(emailUser: String): User?

}