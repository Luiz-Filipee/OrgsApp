package br.luizfilipe.orgs.data.database.dao.memories

import br.luizfilipe.orgs.data.database.dao.LocalProdutoDataSource
import br.luizfilipe.orgs.data.model.Produto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import java.util.concurrent.ConcurrentHashMap

class InMemoryProdutoDataSource : LocalProdutoDataSource {

    private val produtos = ConcurrentHashMap<Long, Produto>()
    private val produtosFlow = MutableStateFlow<List<Produto>>(emptyList())

    override fun buscaTodos(): Flow<List<Produto>> = produtosFlow

    override fun buscaPorId(idUser: Long): Flow<Produto> = flow {
        produtos[idUser]?.let { emit(it) }
    }

    override suspend fun somaTodosValores(usuarioId: Long): Double? {
        TODO("Not yet implemented")
    }

    override fun buscaTodosDoUsuario(usuarioId: Long): Flow<List<Produto>> {
        TODO("Not yet implemented")
    }

    override fun buscaPorNome(nomeBuscado: String): Flow<Produto> = flow {
        produtos.values.forEach { produto ->
            if (produto.nome == nomeBuscado) {
                emit(produto)
            }
        }
    }

    override suspend fun buscaTodosOrdenaPorValorAsc(): List<Produto>? {
        TODO("Not yet implemented")
    }

    override suspend fun buscaTodosOrdenaPorValorDesc(): List<Produto>? {
        TODO("Not yet implemented")
    }


    override suspend fun salva(o: Produto) {
        produtos[o.id] = o
        produtosFlow.update { produtos.values.toList() }
    }

    override suspend fun remove(o: Produto) {
        produtos.remove(o.id)
    }

    override suspend fun update(o: Produto) {
        produtos.remove(o.id)
        produtosFlow.update { produtos.values.toList() }
    }
}