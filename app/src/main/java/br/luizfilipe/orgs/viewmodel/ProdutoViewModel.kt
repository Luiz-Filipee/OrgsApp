package br.luizfilipe.orgs.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.luizfilipe.orgs.data.database.repository.ProdutoRepository
import br.luizfilipe.orgs.data.database.repository.ProdutoRepositoryImp
import br.luizfilipe.orgs.data.model.Produto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ProdutoViewModel(
    private val produtoRepository: ProdutoRepository
) : ViewModel() {

    fun salva(produto: Produto) = viewModelScope.launch {
        produtoRepository.salva(produto)
    }

    fun remove(produto: Produto) = viewModelScope.launch {
        produtoRepository.remove(produto)
    }

    fun buscaPorId(id: Long): Flow<Produto> {
        return produtoRepository.buscaPorId(id)
    }

    suspend fun somaTodosValores(id: Long): Double? {
        return produtoRepository.somaTodosValores(id)
    }

    fun buscaTodosDoUsuario(usuarioId: Long): Flow<List<Produto>>{
        return produtoRepository.buscaTodosDoUsuario(usuarioId)
    }

     fun buscaPorNome(nomeBuscado: String): Flow<Produto> {
        return produtoRepository.buscaPorNome(nomeBuscado)
    }

     suspend fun buscaTodosOrdenaPorValorAsc(): List<Produto>? {
        return produtoRepository.buscaTodosOrdenaPorValorAsc()
    }

    suspend fun buscaTodosOrdenaPorValorDesc(): List<Produto>? {
        return produtoRepository.buscaTodosOrdenaPorValorAsc()
    }
}