package br.luizfilipe.orgs.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import br.luizfilipe.orgs.model.Produto

@Dao
interface ProdutoDAORoom {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun salva(o: Produto)

    @Delete
    fun remove(o: Produto)

    @Update
    fun upadate(o: Produto)

    @Query("SELECT * FROM produto")
    fun buscaTodos(): List<Produto>

    @Query("SELECT * FROM produto WHERE id = :id")
    fun buscaPorId(id: Long): Produto?

    @Query("SELECT * FROM produto ORDER BY nome ASC")
    fun buscaTodosOrdenaPorNomeAsc(): List<Produto>?

    @Query("SELECT * FROM produto ORDER BY nome DESC")
    fun buscaTodosOrdenaPorNomeDesc(): List<Produto>?

    @Query("SELECT * FROM produto ORDER BY descricao ASC")
    fun buscaTodosOrdenaPorDescricaoAsc(): List<Produto>?

    @Query("SELECT * FROM produto ORDER BY descricao DESC")
    fun buscaTodosOrdenaPorDescricaoDesc(): List<Produto>?

    @Query("SELECT * FROM produto ORDER BY valor ASC")
    fun buscaTodosOrdenaPorValorAsc(): List<Produto>?

    @Query("SELECT * FROM produto ORDER BY valor DESC")
    fun buscaTodosOrdenaPorValorDesc(): List<Produto>?
}