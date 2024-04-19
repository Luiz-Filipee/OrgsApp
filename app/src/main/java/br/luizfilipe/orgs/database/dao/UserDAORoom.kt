package br.luizfilipe.orgs.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import br.luizfilipe.orgs.model.User

@Dao
interface UserDAORoom {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun salva(o: User)

    @Delete
    fun remove(o: User)

    @Update
    fun upadate(o: User)

    @Query("SELECT * FROM user")
    fun buscaTodos(): List<User>
    @Query("SELECT * FROM user WHERE id = :idUser")
    fun buscaPorId(idUser: Long): User?

    @Query("SELECT * FROM user WHERE email = :username AND senha = :senha")
    fun validaUser(username :String, senha :String): User?
}