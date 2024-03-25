package br.luizfilipe.orgs.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.luizfilipe.orgs.database.AppDataBase
import br.luizfilipe.orgs.database.dao.ProdutoDAORoom
import br.luizfilipe.orgs.databinding.ProdutoItemBinding
import br.luizfilipe.orgs.extensions.formataParaMoedaBrasileira
import br.luizfilipe.orgs.extensions.tentaCarregarImagem
import br.luizfilipe.orgs.model.Produto
import java.util.Collections

class ListaProdutosAdapter(
    produtos: List<Produto> = emptyList(),
    private val context: Context,
    var quandoClicaNoItem: (produto: Produto) -> Unit = {},
    private val produtoDAORoom: ProdutoDAORoom
) : RecyclerView.Adapter<ListaProdutosAdapter.ProdutoViewHolder>() {

    private val produtos = produtos.toMutableList()

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

    fun troca(positionInitial: Int, positionFinal: Int) {
        Collections.swap(produtos, positionInitial, positionFinal)
        notifyItemMoved(positionInitial, positionFinal)
        val dao = AppDataBase.getInstance(context).produtoDaoRoom()
        for (produto in produtos){
            dao.upadate(produto)
        }
    }

    fun remove(position: Int) {
        val produto = produtos.get(position)
        produtos.removeAt(position)
        notifyItemRemoved(position)
        Thread{
            produtoDAORoom.remove(produto)
        }.start()
    }

    inner class ProdutoViewHolder(private val binding: ProdutoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var produto: Produto

        init {
            itemView.setOnClickListener{
                if(::produto.isInitialized){
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