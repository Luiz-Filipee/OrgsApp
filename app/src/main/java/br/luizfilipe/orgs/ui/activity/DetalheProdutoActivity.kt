package br.luizfilipe.orgs.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.luizfilipe.orgs.databinding.ActivityDetalheProdutoBinding
import br.luizfilipe.orgs.ui.activity.ConstanteActivities.Companion.CHAVE_PRODUTO
import br.luizfilipe.orgs.extensions.formataParaMoedaBrasileira
import br.luizfilipe.orgs.extensions.tentaCarregarImagem
import br.luizfilipe.orgs.model.Produto

class DetalheProdutoActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityDetalheProdutoBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        tentaCarregarProduto()
    }

    private fun tentaCarregarProduto(){
        intent.getParcelableExtra<Produto>(CHAVE_PRODUTO)?.let{
            produtoCarregado -> preencheCampos(produtoCarregado)
        } ?: finish()
    }

    private fun preencheCampos(produtoCarregado: Produto){
        // Podemos ler da seguinte maneira, com este objeto 'binding', faça o seguinte
        with(binding){
            activityDetalhesImageview.tentaCarregarImagem(produtoCarregado.imagem)
            activityDetalheTitulo.text = produtoCarregado.nome
            activityDetalheDescricao.text = produtoCarregado.descricao
            activityDetalhesPreco.text = produtoCarregado.valor.formataParaMoedaBrasileira()
        }
    }
}