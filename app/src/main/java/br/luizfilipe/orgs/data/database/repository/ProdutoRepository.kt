package br.luizfilipe.orgs.data.database.repository

import android.content.Context
import br.luizfilipe.orgs.data.database.AppDataBase
import br.luizfilipe.orgs.data.model.Produto
import br.luizfilipe.orgs.data.model.User
import kotlinx.coroutines.flow.Flow

interface ProdutoRepository{

    suspend fun salva(produto: Produto)

    suspend fun remove(produto: Produto)

    fun buscaPorId(id: Long): Flow<Produto>

    fun buscaTodos(): Flow<List<Produto>>

    suspend fun somaTodosValores(id: Long): Double?

    fun buscaTodosDoUsuario(usuarioId: Long): Flow<List<Produto>>

    fun buscaPorNome(nomeBuscado: String): Flow<Produto>

    suspend fun buscaTodosOrdenaPorValorAsc(): List<Produto>?

    suspend fun buscaTodosOrdenaPorValorDesc(): List<Produto>?

}


