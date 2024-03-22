package br.luizfilipe.orgs.ui.dao

import br.luizfilipe.orgs.ui.model.Produto
import java.math.BigDecimal
import java.util.Collections

class ProdutoDAO {

    fun insert(produto: Produto){
         produtos.add(produto)
     }

    fun getAll(): List<Produto>{
        return produtos.toList()
    }

    fun troca(positionInitial: Int, positionFinal: Int) {
        Collections.swap(produtos, positionInitial, positionFinal)
    }

    fun remove(position: Int) {
        produtos.removeAt(position)
    }

    companion object {
        private val produtos = mutableListOf<Produto>(
            Produto("Salada de frutas","Laranja, maçãs e uvas", BigDecimal(19.83))
        )
    }
}