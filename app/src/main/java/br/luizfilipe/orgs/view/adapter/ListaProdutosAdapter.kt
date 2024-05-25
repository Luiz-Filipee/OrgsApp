package br.luizfilipe.orgs.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.luizfilipe.orgs.data.database.AppDataBase
import br.luizfilipe.orgs.data.database.dao.LocalProdutoDataSource
import br.luizfilipe.orgs.data.model.Produto
import br.luizfilipe.orgs.databinding.ProdutoItemBinding
import br.luizfilipe.orgs.extensions.formataParaMoedaBrasileira
import br.luizfilipe.orgs.extensions.tentaCarregarImagem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.java.KoinJavaComponent.inject
import java.util.Collections

class ListaProdutosAdapter(
    produtos: List<Produto> = emptyList(),
    private val context: Context,
    var quandoClicaNoItem: (produto: Produto) -> Unit = {},
) : RecyclerView.Adapter<ListaProdutosAdapter.ProdutoViewHolder>(), KoinComponent {

    private val produtos = produtos.toMutableList()

    private val localProdutoDataSource: LocalProdutoDataSource by inject()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListaProdutosAdapter.ProdutoViewHolder {
        val binding = ProdutoItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ProdutoViewHolder(binding)
    }

    override fun getItemCount(): Int = produtos.size

    override fun onBindViewHolder(holder: ListaProdutosAdapter.ProdutoViewHolder, position: Int) {
        val produto = produtos[position]
        holder.vincula(produto)
    }

    fun atualiza(produtos: List<Produto>) {
        this.produtos.clear()
        this.produtos.addAll(produtos)
        notifyDataSetChanged()
    }

    suspend fun troca(positionInitial: Int, positionFinal: Int) {
        Collections.swap(produtos, positionInitial, positionFinal)
        notifyItemMoved(positionInitial, positionFinal)
        for (produto in produtos) {
            localProdutoDataSource.update(produto)
        }
    }

    fun remove(position: Int) {
        val produto = produtos.get(position)
        produtos.removeAt(position)
        notifyItemRemoved(position)
        GlobalScope.launch(Dispatchers.IO) {
            localProdutoDataSource.remove(produto)
        }.start()
    }

    inner class ProdutoViewHolder(private val binding: ProdutoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var produto: Produto

        init {
            itemView.setOnClickListener {
                if (::produto.isInitialized) {
                    quandoClicaNoItem(produto)
                }
            }
        }

        fun vincula(produto: Produto) {
            this.produto = produto
            val nome = binding.produtoItemTitulo
            nome.text = produto.nome
            val descricao = binding.produtoItemDescricao
            descricao.text = produto.descricao
            val valor = binding.produtoItemValor
            val valorMoeda: String = produto.valor.formataParaMoedaBrasileira()
            valor.text = valorMoeda

            /*val visibilidade = if(produto.imagem != null){
                View.VISIBLE
            }else{
                View.GONE
            }

            binding.produtoItemImagem.visibility = visibilidade*/

            binding.produtoItemImagem.tentaCarregarImagem(produto.imagem)
        }

    }

}