package br.luizfilipe.orgs.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.luizfilipe.orgs.model.Produto

@Dao
interface ProdutoDAORoom {


    @Query("SELECT * FROM produto")
    fun buscaTodos(): List<Produto>

    @Insert
    fun salva(produto: Produto)

    @Delete
    fun remove(produto: Produto)

    @Update
    fun upadate(produto: Produto)
}