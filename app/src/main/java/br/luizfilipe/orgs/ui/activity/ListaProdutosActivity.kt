package br.luizfilipe.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import br.luizfilipe.orgs.R
import br.luizfilipe.orgs.database.AppDataBase
import br.luizfilipe.orgs.database.dao.ProdutoDAORoom
import br.luizfilipe.orgs.databinding.ActivityListaProdutoBinding
import br.luizfilipe.orgs.model.Produto
import br.luizfilipe.orgs.ui.activity.ConstanteActivities.Companion.CHAVE_PRODUTO_ID
import br.luizfilipe.orgs.ui.activity.ConstanteActivities.Companion.CHAVE_USER_ID
import br.luizfilipe.orgs.ui.activity.ConstanteActivities.Companion.TITULO_APPBAR
import br.luizfilipe.orgs.ui.adapter.ListaProdutosAdapter
import br.luizfilipe.orgs.ui.helpercallback.ProdutoItemTouchHelperCallback
import kotlinx.coroutines.runBlocking


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
    private var idUser = 0L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = TITULO_APPBAR
        setContentView(binding.root)
        adapter = ListaProdutosAdapter(
            context = this,
            produtoDAORoom = produtoDao
        )
        configuraRecyclerView()
        configuraFab()
        tentaCarregarUser()
    }

    override fun onResume() {
        super.onResume()
        adapter.atualiza(produtoDao.buscaTodos())
    }

    private fun configuraRecyclerView() {
        val recyclerView = binding.listaProdutoActivityRecyclerview
        recyclerView.adapter = adapter
        adapter.quandoClicaNoItem = {
            val intent = Intent(
                this,
                DetalheProdutoActivity::class.java
            ).apply {
                putExtra(CHAVE_PRODUTO_ID, it.id)
            }
            startActivity(intent)
        }
        congiguraItemTouchHelper(recyclerView)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_ordena_itens, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.perfil){
                abrePerfilUser()
        }

        val produtosOrdenados: List<Produto>? = when (item.itemId) {
            R.id.nome_asc ->
                produtoDao.buscaTodosOrdenaPorNomeAsc()

            R.id.nome_desc ->
                produtoDao.buscaTodosOrdenaPorNomeDesc()

            R.id.descricao_asc ->
                produtoDao.buscaTodosOrdenaPorDescricaoAsc()

            R.id.descricao_desc ->
                produtoDao.buscaTodosOrdenaPorDescricaoDesc()

            R.id.valor_desc ->
                produtoDao.buscaTodosOrdenaPorValorDesc()

            R.id.valor_asc ->
                produtoDao.buscaTodosOrdenaPorValorAsc()

            R.id.sem_ordem ->
                produtoDao.buscaTodos()

            else -> null
        }
        produtosOrdenados?.let { // pode encontrar ou nao um produto
            adapter.atualiza(it) //atualiza o adapter a cada vez que um menu for selecionado
        }
        return super.onOptionsItemSelected(item)
    }

    private fun tentaCarregarUser() {
        idUser = intent.getLongExtra(CHAVE_USER_ID, 0L)
    }

    private fun abrePerfilUser(){
        val userId = userDao.buscaPorId(idUser)
        if(userId != null){
            val intent = Intent(this, PerfilUserActivity::class.java).apply {
                putExtra(CHAVE_USER_ID, userId)
            }
            startActivity(intent)
        }
    }

    private fun congiguraItemTouchHelper(recyclerView: RecyclerView) {
        val itemTouchHelper = ItemTouchHelper(ProdutoItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun configuraFab() {
        val floatingActionButton =
            binding.extendedFab
        floatingActionButton.setOnClickListener(View.OnClickListener {
            vaiParaFormularioProduto()
        })
    }

    private fun vaiParaFormularioProduto() {
        val intent = Intent(this, FormularioProdutoActivity::class.java)
        startActivity(intent)
    }

}