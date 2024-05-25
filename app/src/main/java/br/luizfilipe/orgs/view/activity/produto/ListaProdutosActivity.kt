package br.luizfilipe.orgs.view.activity.produto

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.PopupMenu
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import br.luizfilipe.orgs.R
import br.luizfilipe.orgs.data.database.AppDataBase
import br.luizfilipe.orgs.databinding.ActivityListaProdutoBinding
import br.luizfilipe.orgs.extensions.vaiPara
import br.luizfilipe.orgs.view.activity.ConstanteActivities
import br.luizfilipe.orgs.view.activity.ConstanteActivities.Companion.CHAVE_PRODUTO_ID
import br.luizfilipe.orgs.view.activity.UsuarioBaseActivity
import br.luizfilipe.orgs.view.activity.user.CadastroUserActivity
import br.luizfilipe.orgs.view.activity.user.OptionsUserActivity
import br.luizfilipe.orgs.view.activity.user.PerfilUserActivity
import br.luizfilipe.orgs.view.adapter.ListaProdutosAdapter
import br.luizfilipe.orgs.view.helpercallback.ProdutoItemTouchHelperCallback
import br.luizfilipe.orgs.viewmodel.ProdutoViewModel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListaProdutosActivity : UsuarioBaseActivity() {

//    private val adapter = ListaProdutosAdapter(context = this)

    private val adapter: ListaProdutosAdapter by inject()

    private val binding by lazy { // so e iniciada qunado acessada pela primeira vez
        ActivityListaProdutoBinding.inflate(layoutInflater) // inflando o layout associado a essa activity
    }

    private val produtoViewModel: ProdutoViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = ConstanteActivities.TITULO_APPBAR
        setContentView(binding.root)
        configuraRecyclerView()
        configuraFab()
        abreMenuOrdena()
        menuNavegacaoListaProdutos()
        pesquisaITem()
        lifecycleScope.launch {
            launch {
                usuario
                    .filterNotNull()
                    .collect { usuario ->
                        buscaProdutosUsuario(usuario.id)
                    }
            }
        }
    }

    private suspend fun buscaProdutosUsuario(usuarioId: Long) {
        produtoViewModel.buscaTodosDoUsuario(usuarioId).collect { produtos ->
            adapter.atualiza(produtos)
        }
    }

    private fun pesquisaITem() {
        binding.imagemIconSearch.setOnClickListener(OnClickListener {
            val termoPesquisa = binding.listaProdutoActivityPesquisa.text.toString()
            if (termoPesquisa.isNotEmpty()) {
                lifecycleScope.launch {
                    buscaProdutoLista(termoPesquisa)
                }
            }
        })
    }

    private suspend fun buscaProdutoLista(termoPesquisa: String) {
        produtoViewModel.buscaPorNome(termoPesquisa).collect {
            vaiPara(DetalheProdutoActivity::class.java)
        }
    }

    private fun configuraRecyclerView() {
        val recyclerView = binding.listaProdutoActivityRecyclerview
        recyclerView.adapter = adapter
        adapter.quandoClicaNoItem = { produto ->
            val intent = Intent(
                this,
                DetalheProdutoActivity::class.java
            ).apply {
                putExtra(CHAVE_PRODUTO_ID, produto.id)
            }
            startActivity(intent)
        }
        congiguraItemTouchHelper(recyclerView)
    }

    private fun abreMenuOrdena() {
        val buttonFiltro = binding.listaProdutoActivityFiltrosOrdena
        buttonFiltro.setOnClickListener { view ->
            val popupMenu = PopupMenu(this, view)
            popupMenu.menuInflater.inflate(R.menu.menu_ordena_itens, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                lifecycleScope.launch {
                    val produtosOrdenados = when (menuItem.itemId) {
                        R.id.valor_asc -> produtoViewModel.buscaTodosOrdenaPorValorDesc()
                        R.id.valor_desc -> produtoViewModel.buscaTodosOrdenaPorValorAsc()
                        else -> emptyList()
                    }
                    if (produtosOrdenados != null) {
                        adapter.atualiza(produtosOrdenados)
                    }
                }
                true
            }
            popupMenu.show()
        }
    }


    private fun menuNavegacaoListaProdutos() {
        val botoesMenuNavegacao = binding.bottomNavigation
        botoesMenuNavegacao.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_gastos -> {
                    vaiPara(GastosProdutosActivity::class.java)
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.item_perfil -> {
                    vaiPara(PerfilUserActivity::class.java)
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.item_settings -> {
                    vaiPara(OptionsUserActivity::class.java)
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.item_sair -> {
                    lifecycleScope.launch {
                        deslogaUsuario()
                    }
                    return@setOnNavigationItemSelectedListener true
                }

                else -> return@setOnNavigationItemSelectedListener false
            }
        }
    }

    private fun congiguraItemTouchHelper(recyclerView: RecyclerView) {
        val itemTouchHelper = ItemTouchHelper(ProdutoItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun configuraFab() {
        binding.extendedFab.setOnClickListener(View.OnClickListener {
            vaiParaFormularioProduto()
        })
    }

    private fun vaiParaFormularioProduto() {
        val intent = Intent(this, FormularioProdutoActivity::class.java)
        startActivity(intent)
    }

}