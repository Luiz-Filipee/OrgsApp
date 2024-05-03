package br.luizfilipe.orgs.ui.activity.produto

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
import kotlinx.coroutines.launch

class DetalheProdutoActivity : AppCompatActivity() {
    private var produto: Produto? = null
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_edita_nota, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val db = AppDataBase.getInstance(this).produtoDaoRoom()
        when (item.itemId) {
            R.id.edit -> {
                vaiPara(FormularioProdutoActivity::class.java)
            }

            R.id.remove -> {
                lifecycleScope.launch {
                    produto?.let {
                        db.remove(it)
                    }
                    finish()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun tentaCarregarProduto() {
        lifecycleScope.launch {
            dataStore.data.collect { preferences ->
                preferences[produtoCadastrado]?.let { produtoId ->
                    produtoDAORoom.buscaPorId(produtoId).collect { produto ->
                        if (produto != null) {
                            preencheCampos(produto)
                        }
                    }
                }
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