package br.luizfilipe.orgs.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import br.luizfilipe.orgs.R
import br.luizfilipe.orgs.ui.model.Produto

class ListaProdutosAdapter(
    private val produtos: List<Produto>,
    private val context: Context
) : RecyclerView.Adapter<ListaProdutosAdapter.ProdutoViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListaProdutosAdapter.ProdutoViewHolder {
        val viewCriada: View =
            LayoutInflater.from(context).inflate(R.layout.produto_item, parent, false)
        return ProdutoViewHolder(viewCriada)
    }

    override fun getItemCount(): Int = produtos.size

    override fun onBindViewHolder(holder: ListaProdutosAdapter.ProdutoViewHolder, position: Int) {
        val produto = produtos[position]
        holder.vincula(produto)
    }

    class ProdutoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun vincula(produto: Produto) {
            val nome = itemView.findViewById<TextView>(R.id.produto_item_titulo)
            nome.text = produto.nome
            val descricao = itemView.findViewById<TextView>(R.id.produto_item_descricao)
            descricao.text = produto.descricao
            val valor = itemView.findViewById<TextView>(R.id.produto_item_valor)
            valor.text = produto.valor.toPlainString()
        }

    }

}