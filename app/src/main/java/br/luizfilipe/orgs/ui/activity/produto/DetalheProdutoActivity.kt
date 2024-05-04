package br.luizfilipe.orgs.ui.activity.produto

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.luizfilipe.orgs.R
import br.luizfilipe.orgs.database.AppDataBase
import br.luizfilipe.orgs.databinding.ActivityDetalheProdutoBinding
import br.luizfilipe.orgs.extensions.formataParaMoedaBrasileira
import br.luizfilipe.orgs.extensions.tentaCarregarImagem
import br.luizfilipe.orgs.extensions.vaiPara
import br.luizfilipe.orgs.model.Produto
import br.luizfilipe.orgs.preferences.dataStore
import br.luizfilipe.orgs.preferences.produtoCadastrado
import br.luizfilipe.orgs.ui.activity.ConstanteActivities
import kotlinx.coroutines.launch

class DetalheProdutoActivity : AppCompatActivity() {
    private var produto: Produto? = null
    private var produtoId: Long = 0L
    private val binding by lazy {
        ActivityDetalheProdutoBinding.inflate(layoutInflater)
    }
    private val produtoDAORoom by lazy {
        AppDataBase.getInstance(this).produtoDaoRoom()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        tentaCarregarProduto()
    }

    override fun onResume() {
        super.onResume()
        buscaProduto()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_edita_nota, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.edit -> {
                Intent(this, FormularioProdutoActivity::class.java).apply {
                    putExtra(ConstanteActivities.CHAVE_PRODUTO_ID, produtoId)
                    startActivity(this)
                }
            }

            R.id.remove -> {
               produto?.let {
                   lifecycleScope.launch {
                       produtoDAORoom.remove(it)
                       finish()
                   }
               }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun tentaCarregarProduto() {
        produtoId = intent.getLongExtra(ConstanteActivities.CHAVE_PRODUTO_ID, 0L)
    }


    private fun buscaProduto() {
        lifecycleScope.launch {
            produtoDAORoom.buscaPorId(produtoId).collect { produtoEncontrado ->
                produto = produtoEncontrado
                produto?.let {
                    preencheCampos(it)
                } ?: finish()
            }
        }
    }

    private fun preencheCampos(produtoCarregado: Produto) {
        // Podemos ler da seguinte maneira, com este objeto 'binding', faça o seguinte
        with(binding) {
            activityDetalhesImageview.tentaCarregarImagem(produtoCarregado.imagem)
            activityDetalheTitulo.text = produtoCarregado.nome
            activityDetalheDescricao.text = produtoCarregado.descricao
            activityDetalhesPreco.text = produtoCarregado.valor.formataParaMoedaBrasileira()
        }
    }
}