package br.luizfilipe.orgs.data.database.repository

import android.util.Log
import br.luizfilipe.orgs.data.database.dao.LocalProdutoDataSource
import br.luizfilipe.orgs.data.model.Produto
import br.luizfilipe.orgs.data.model.User
import kotlinx.coroutines.flow.Flow

private const val TAG = "ProdutoRepositoryImp"

class ProdutoRepositoryImp(
    private val localProdutoDataSource: LocalProdutoDataSource
) : ProdutoRepository {

    override suspend fun salva(produto: Produto) {
        try {

            localProdutoDataSource.salva(produto)

        } catch (e: Exception) {

            Log.e(TAG, "salva: falha ao salvar produto", e)

        }
    }

    override suspend fun remove(produto: Produto) {
        try {

            localProdutoDataSource.remove(produto)

        } catch (e: Exception) {

            Log.e(TAG, "salva: falha ao remover produto", e)

        }
    }

    override fun buscaPorId(id: Long): Flow<Produto> {
        return localProdutoDataSource.buscaPorId(id)
    }

    override fun buscaTodos(): Flow<List<Produto>> {
        return localProdutoDataSource.buscaTodos()
    }

    override suspend fun somaTodosValores(id: Long): Double? {
        return localProdutoDataSource.somaTodosValores(id)
    }

    override fun buscaTodosDoUsuario(usuarioId: Long): Flow<List<Produto>> {
       return localProdutoDataSource.buscaTodosDoUsuario(usuarioId)
    }

    override fun buscaPorNome(nomeBuscado: String): Flow<Produto> {
        return localProdutoDataSource.buscaPorNome(nomeBuscado)
    }

    override suspend fun buscaTodosOrdenaPorValorAsc(): List<Produto>? {
        return localProdutoDataSource.buscaTodosOrdenaPorValorAsc()
    }

    override suspend fun buscaTodosOrdenaPorValorDesc(): List<Produto>? {
        return localProdutoDataSource.buscaTodosOrdenaPorValorAsc()
    }
}