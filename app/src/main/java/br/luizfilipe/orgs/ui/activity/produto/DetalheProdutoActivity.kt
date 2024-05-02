package br.luizfilipe.orgs.ui.activity.produto

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import br.luizfilipe.orgs.R
import br.luizfilipe.orgs.database.AppDataBase
import br.luizfilipe.orgs.databinding.ActivityDetalheProdutoBinding
import br.luizfilipe.orgs.extensions.formataParaMoedaBrasileira
import br.luizfilipe.orgs.extensions.tentaCarregarImagem
import br.luizfilipe.orgs.model.Produto
import br.luizfilipe.orgs.ui.activity.ConstanteActivities
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetalheProdutoActivity : AppCompatActivity() {
    private var produtoId: Long = 0
    private var produto: Produto? = null
    private val binding by lazy {
        ActivityDetalheProdutoBinding.inflate(layoutInflater)
    }
    private val produtoDAORoom by lazy {
        AppDataBase.getInstance(this).produtoDaoRoom()
    }
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        tentaCarregarProduto()
    }

    override fun onResume() {
        super.onResume()
        buscaProduto()
    }

    private fun buscaProduto() {
        scope.launch {
            produto = produtoDAORoom.buscaPorId(produtoId) // buscando um produto no DAO
            withContext(Dispatchers.Main) {
                produto?.let { // pode ou nao encontrar um produto
                    preencheCampos(it) // preenche os campos do produto
                } ?: finish() // caso seja nulo ele finaliza a activity
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_edita_nota, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val db = AppDataBase.getInstance(this).produtoDaoRoom()
        when (item.itemId) {
            R.id.edit -> {
                Intent(this, FormularioProdutoActivity::class.java).apply {
                    putExtra(ConstanteActivities.CHAVE_PRODUTO_ID, produtoId)
                    startActivity(this)
                }
            }

            R.id.remove -> {
                scope.launch {
                    produto?.let { db.remove(it) }
                    finish()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun tentaCarregarProduto() {
        produtoId = intent.getLongExtra(ConstanteActivities.CHAVE_PRODUTO_ID, 0)
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