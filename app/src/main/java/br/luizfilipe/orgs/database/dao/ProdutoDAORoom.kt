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

    @Query("SELECT * FROM produto WHERE nome = :nomeBuscado")
    suspend fun buscaPorNome(nomeBuscado: String): Produto?

    @Query("SELECT * FROM produto ORDER BY nome ASC")
    suspend fun buscaTodosOrdenaPorNomeAsc(): List<Produto>?

    @Query("SELECT * FROM produto ORDER BY nome DESC")
    suspend fun buscaTodosOrdenaPorNomeDesc(): List<Produto>?

    @Query("SELECT * FROM produto ORDER BY descricao ASC")
    suspend fun buscaTodosOrdenaPorDescricaoAsc(): List<Produto>?

    @Query("SELECT * FROM produto ORDER BY descricao DESC")
    suspend fun buscaTodosOrdenaPorDescricaoDesc(): List<Produto>?

    @Query("SELECT * FROM produto ORDER BY valor ASC")
    suspend fun buscaTodosOrdenaPorValorAsc(): List<Produto>?

    @Query("SELECT * FROM produto ORDER BY valor DESC")
    suspend fun buscaTodosOrdenaPorValorDesc(): List<Produto>?
}