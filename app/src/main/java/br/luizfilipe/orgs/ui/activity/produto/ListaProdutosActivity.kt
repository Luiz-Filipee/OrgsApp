package br.luizfilipe.orgs.ui.activity.produto

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import br.luizfilipe.orgs.R
import br.luizfilipe.orgs.database.AppDataBase
import br.luizfilipe.orgs.databinding.ActivityListaProdutoBinding
import br.luizfilipe.orgs.extensions.vaiPara
import br.luizfilipe.orgs.preferences.dataStore
import br.luizfilipe.orgs.preferences.produtoCadastrado
import br.luizfilipe.orgs.preferences.usuarioLogadoPreferences
import br.luizfilipe.orgs.ui.activity.ConstanteActivities
import br.luizfilipe.orgs.ui.activity.user.CadastroUserActivity
import br.luizfilipe.orgs.ui.activity.user.LoginActivity
import br.luizfilipe.orgs.ui.activity.user.PerfilUserActivity
import br.luizfilipe.orgs.ui.adapter.ListaProdutosAdapter
import br.luizfilipe.orgs.ui.helpercallback.ProdutoItemTouchHelperCallback
import kotlinx.coroutines.launch

class ListaProdutosActivity() : AppCompatActivity() {

    private lateinit var adapter: ListaProdutosAdapter
    private val binding by lazy { // so e iniciada qunado acessada pela primeira vez
        ActivityListaProdutoBinding.inflate(layoutInflater) // inflando o layout associado a essa activity
    }
    private val produtoDao by lazy {
        val db = AppDataBase.getInstance(this)
        db.produtoDaoRoom()
    }
    private val userDao by lazy {
        val db = AppDataBase.getInstance(this)
        db.userDaoRoom()
    }

    private var idUser: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = ConstanteActivities.TITULO_APPBAR
        setContentView(binding.root)
        configuraAdapter()
        configuraRecyclerView()
        configuraFab()
        abreMenuOrdena()
        menunavigation()
        pesquisaITem()
        tentaCarregarUser()
    }

    private fun tentaCarregarUser() {
        lifecycleScope.launch {

            dataStore.data.collect { preferences ->
                preferences[usuarioLogadoPreferences]?.let { usuarioId ->
                    launch {
                        userDao.buscaPorId(usuarioId).collect { user ->
                            Log.i("ListaProdutos", "tentaCarregarUser: $user")
                        }
                    }
                } ?: vaiParaLogin()
            }
        }
    }

    private fun vaiParaLogin() {
        vaiPara(LoginActivity::class.java)
        finish()
    }

    private fun pesquisaITem() {
        binding.imagemIconSearch.setOnClickListener(OnClickListener {
            val termoPesquisa = binding.listaProdutoActivityPesquisa.text.toString()
            if (termoPesquisa.isNotEmpty()) {
                lifecycleScope.launch {
                    produtoDao.buscaPorNome(termoPesquisa.lowercase())?.let { produto ->
                        vaiPara(DetalheProdutoActivity::class.java) {
                            putExtra(ConstanteActivities.CHAVE_PRODUTO_ID, produto.id)
                        }
                    }
                }
            }
        })
    }

    private fun configuraAdapter() {
        adapter = ListaProdutosAdapter(
            context = this,
            produtoDAORoom = produtoDao
        )
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            val produtos = produtoDao.buscaTodos()
            adapter.atualiza(produtos)
        }
    }

    private fun configuraRecyclerView() {
        val recyclerView = binding.listaProdutoActivityRecyclerview
        recyclerView.adapter = adapter
        adapter.quandoClicaNoItem = { produto ->
            lifecycleScope.launch {
                dataStore.edit { preferences ->
                    preferences[produtoCadastrado] = produto.id
                }
                vaiPara(DetalheProdutoActivity::class.java)
            }
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
                        R.id.valor_asc -> produtoDao.buscaTodosOrdenaPorValorAsc()
                        R.id.valor_desc -> produtoDao.buscaTodosOrdenaPorValorDesc()
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


    private fun menunavigation() {
        val buttonNavigation = binding.bottomNavigation
        buttonNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_gastos -> {
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.item_perfil -> {
                    vaiPara(PerfilUserActivity::class.java)
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.item_settings -> {
                    vaiPara(CadastroUserActivity::class.java)
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.item_sair -> {
                    lifecycleScope.launch {
                        dataStore.edit { preferences ->
                            preferences.remove(usuarioLogadoPreferences)
                        }
                    }
                    return@setOnNavigationItemSelectedListener true
                }

                else -> return@setOnNavigationItemSelectedListener false
            }
        }
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        var produtosOrdenados: List<Produto>? = null
//
//        lifecycleScope.launch {
//            produtosOrdenados = when (item.itemId) {
//
//                R.id.valor_desc ->
//                    produtoDao.buscaTodosOrdenaPorValorDesc()
//
//                R.id.valor_asc ->
//                    produtoDao.buscaTodosOrdenaPorValorAsc()
//
//                else -> null
//            }
//        }
//        produtosOrdenados?.let { // pode encontrar ou nao um produto
//            adapter.atualiza(it) //atualiza o adapter a cada vez que um menu for selecionado
//        }
//        return super.onOptionsItemSelected(item)
//    }

    private fun congiguraItemTouchHelper(recyclerView: RecyclerView) {
        val itemTouchHelper = ItemTouchHelper(ProdutoItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun configuraFab() {
        binding.extendedFab.setOnClickListener(View.OnClickListener {
            vaiPara(FormularioProdutoActivity::class.java)
        })
    }

}