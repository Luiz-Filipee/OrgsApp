package br.luizfilipe.orgs.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.luizfilipe.orgs.databinding.ProdutoItemGastosBinding
import br.luizfilipe.orgs.extensions.formataParaMoedaBrasileira
import br.luizfilipe.orgs.data.model.Produto

class ListaProdutosGastosAdapter(
    private val context: Context
) : RecyclerView.Adapter<ListaProdutosGastosAdapter.ProdutoViewHolder>() {

    private val produtos = mutableListOf<Produto>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListaProdutosGastosAdapter.ProdutoViewHolder {
        val binding = ProdutoItemGastosBinding.inflate(LayoutInflater.from(context), parent, false)
        return ProdutoViewHolder(binding)
    }

    override fun getItemCount(): Int = produtos.size

    override fun onBindViewHolder(
        holder: ListaProdutosGastosAdapter.ProdutoViewHolder,
        position: Int
    ) {
        val produto = produtos[position]
        holder.vincula(produto)
    }

    fun atualiza(produtos: List<Produto>) {
        this.produtos.clear()
        this.produtos.addAll(produtos)
        notifyDataSetChanged()
    }


    inner class ProdutoViewHolder(private val binding: ProdutoItemGastosBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var produto: Produto

        fun vincula(produto: Produto) {
            this.produto = produto
            val nome = binding.produtoItemGastosTitulo
            nome.text = produto.nome
            val valor = binding.produtoItemGastosValor
            val valorMoeda: String = produto.valor.formataParaMoedaBrasileira()
            valor.text = valorMoeda
        }

    }
}