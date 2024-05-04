package br.luizfilipe.orgs.ui.activity.produto

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import br.luizfilipe.orgs.database.AppDataBase
import br.luizfilipe.orgs.databinding.ActivityGastosProdutosBinding
import br.luizfilipe.orgs.ui.activity.UsuarioBaseActivity
import br.luizfilipe.orgs.ui.adapter.ListaProdutosGastosAdapter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

class GastosProdutosActivity : UsuarioBaseActivity() {
    private val adapter = ListaProdutosGastosAdapter(context = this)
    private val binding by lazy {
        ActivityGastosProdutosBinding.inflate(layoutInflater)
    }
    private val produtoDao by lazy {
        AppDataBase.getInstance(this).produtoDaoRoom()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        lifecycleScope.launch {
            launch {
                usuario
                    .filterNotNull()
                    .collect { usuario ->
                        alocaComponentes(usuario.id)
                        buscaProdutosUsuario(usuario.id)
                    }
            }
        }
        configuraRecyclerView()
        binding.activityGastosVoltar.setOnClickListener(View.OnClickListener {
            finish()
        })
    }

    private fun alocaComponentes(idUsuario: Long) {
        lifecycleScope.launch {
            produtoDao.somaTodosValores(idUsuario)?.let {
                preencheCampos(it)
            }
        }
    }

    private fun preencheCampos(valor: Double) {
        val valorFormatado = String.format("%.2f", valor)
        binding.activityGastosValor.text = valorFormatado
    }

    private suspend fun buscaProdutosUsuario(usuarioId: Long) {
        produtoDao.buscaTodosDoUsuario(usuarioId).collect { produtos ->
            Log.i("GastosProdutos", "buscaProdutosUsuario: $produtos")
            adapter.atualiza(produtos)
        }
    }

    private fun configuraRecyclerView() {
        val recyclerView = binding.activityGastosRecyclerView
        recyclerView.adapter = adapter
    }
}