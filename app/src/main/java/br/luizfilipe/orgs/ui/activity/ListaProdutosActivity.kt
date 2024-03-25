package br.luizfilipe.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import br.luizfilipe.orgs.database.AppDataBase
import br.luizfilipe.orgs.database.dao.ProdutoDAORoom
import br.luizfilipe.orgs.databinding.ActivityListaProdutoBinding
import br.luizfilipe.orgs.ui.activity.ConstanteActivities.Companion.CHAVE_PRODUTO
import br.luizfilipe.orgs.ui.activity.ConstanteActivities.Companion.TITULO_APPBAR
import br.luizfilipe.orgs.ui.adapter.ListaProdutosAdapter
import br.luizfilipe.orgs.ui.helpercallback.ProdutoItemTouchHelperCallback


class ListaProdutosActivity() : AppCompatActivity() {

    private lateinit var produtoDAORoom: ProdutoDAORoom
    private lateinit var adapter: ListaProdutosAdapter
    private val binding by lazy {
        ActivityListaProdutoBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = TITULO_APPBAR
        setContentView(binding.root)

        produtoDAORoom = AppDataBase.getInstance(this).produtoDaoRoom()
        adapter = ListaProdutosAdapter(
            context = this,
           produtoDAORoom =  produtoDAORoom
        )
        configuraRecyclerView()
        configuraFab()
    }

    override fun onResume() {
        super.onResume()
        val db = AppDataBase.getInstance(this)
        val produtoDaoRoom = db.produtoDaoRoom()
        adapter.atualiza(produtoDaoRoom.buscaTodos())
    }

    private fun configuraRecyclerView() {
        val recyclerView = binding.listaProdutoActivityRecyclerview
        recyclerView.adapter = adapter
        adapter.quandoClicaNoItem = {
            val intent = Intent(
                this,
                DetalheProdutoActivity::class.java
            ).apply {
                putExtra(CHAVE_PRODUTO, it)
            }
            startActivity(intent)
        }
        congiguraItemTouchHelper(recyclerView)
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