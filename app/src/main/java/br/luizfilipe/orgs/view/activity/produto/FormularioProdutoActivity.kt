package br.luizfilipe.orgs.view.activity.produto

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import br.luizfilipe.orgs.data.model.Produto
import br.luizfilipe.orgs.databinding.ActivityFormularioProdutoBinding
import br.luizfilipe.orgs.extensions.tentaCarregarImagem
import br.luizfilipe.orgs.view.activity.ConstanteActivities
import br.luizfilipe.orgs.view.activity.UsuarioBaseActivity
import br.luizfilipe.orgs.view.dialog.FormularioImagemDialog
import br.luizfilipe.orgs.viewmodel.ProdutoViewModel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.math.BigDecimal

class FormularioProdutoActivity : UsuarioBaseActivity() {

    private val binding by lazy {
        ActivityFormularioProdutoBinding.inflate(layoutInflater)
    }
    private var url: String? = null
    private var produtoId = 0L

    private val produtoViewModel: ProdutoViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title = "Cadastrar produto"
        configuraBotaoSalvar()
        binding.activityFormularioImagem.setOnClickListener {
            FormularioImagemDialog(this)
                .mostra(url) { imagem ->
                    url = imagem
                    binding.activityFormularioImagem.tentaCarregarImagem(url)
                }
        }
        tentaCarregarProduto()
        lifecycleScope.launch {
            usuario
                .filterNotNull()
                .collect {
                    Log.i("FormularioProduto", "onCreate: $it")
                }
        }

    }

    private fun tentaCarregarProduto() {
        produtoId = intent.getLongExtra(ConstanteActivities.CHAVE_PRODUTO_ID, 0L)
    }

    override fun onResume() {
        super.onResume()
        tentaBuscarProduto()
    }

    private fun tentaBuscarProduto() {
        lifecycleScope.launch {
//            produtoDao.buscaPorId(produtoId).collect {
//                it?.let { produtoEncontrado ->
//                    title = "Alterar Produto"
//                    preencheCampos(produtoEncontrado)
//                }
//            }
        }
    }

    private fun preencheCampos(produto: Produto) {
        url = produto.imagem
        binding.activityFormularioImagem
            .tentaCarregarImagem(produto.imagem)
        binding.activityFormularioProdutoNome
            .setText(produto.nome)
        binding.activityFormularioProdutoDescricao
            .setText(produto.descricao)
        binding.activityFormularioProdutoValor
            .setText(produto.valor.toPlainString())
    }

    private fun configuraBotaoSalvar() {
        val botaoSalvar = binding.activityFormularioButton

        botaoSalvar.setOnClickListener {
            lifecycleScope.launch {
                usuario.value?.let { usuario ->
                    val produtoNovo = criaProduto(usuario.id)
                    if (produtoNovo.valorEhValido) {
                        produtoViewModel.salva(produtoNovo)
                        finish()
                    }
                }
            }
        }
    }

    private fun criaProduto(usuarioId: Long): Produto {
        val campoNome = binding.activityFormularioProdutoNome
        val nome = campoNome.text.toString()
        val campoDescricao = binding.activityFormularioProdutoDescricao
        val descricao = campoDescricao.text.toString()
        val campoValor = binding.activityFormularioProdutoValor
        val valorEmTexto = campoValor.text.toString()
        val valor = if (valorEmTexto.isBlank()) {
            BigDecimal.ZERO
        } else {
            BigDecimal(valorEmTexto)
        }

        return Produto(
            id = produtoId,
            nome = nome,
            descricao = descricao,
            valor = valor,
            imagem = url,
            usuarioId = usuarioId
        )
    }

}
