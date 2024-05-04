package br.luizfilipe.orgs.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import br.luizfilipe.orgs.model.Produto
import kotlinx.coroutines.flow.Flow

@Dao
interface ProdutoDAORoom {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun salva(o: Produto)

    @Delete
    suspend fun remove(o: Produto)

    @Update
    suspend fun upadate(o: Produto)

    @Query("SELECT * FROM produto")
    fun buscaTodos(): Flow<List<Produto>>

    @Query("SELECT * FROM produto WHERE id = :id")
    fun buscaPorId(id: Long): Flow<Produto?>

    @Query("SELECT SUM(valor) FROM produto WHERE usuarioId = :usuarioId")
    suspend fun somaTodosValores(usuarioId: Long): Double?

    @Query("SELECT * FROM produto WHERE usuarioId = :usuarioId")
    fun buscaTodosDoUsuario(usuarioId: Long) : Flow<List<Produto>>

    @Query("SELECT * FROM produto WHERE nome = :nomeBuscado")
    fun buscaPorNome(nomeBuscado: String): Flow<Produto>

    @Query("SELECT * FROM produto ORDER BY valor ASC")
    suspend fun buscaTodosOrdenaPorValorAsc(): List<Produto>?

    @Query("SELECT * FROM produto ORDER BY valor DESC")
    suspend fun buscaTodosOrdenaPorValorDesc(): List<Produto>?
}