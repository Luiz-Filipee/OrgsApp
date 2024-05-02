package br.luizfilipe.orgs.ui.activity.produto

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.luizfilipe.orgs.database.AppDataBase
import br.luizfilipe.orgs.databinding.ActivityFormularioProdutoBinding
import br.luizfilipe.orgs.extensions.tentaCarregarImagem
import br.luizfilipe.orgs.model.Produto
import br.luizfilipe.orgs.ui.activity.ConstanteActivities
import br.luizfilipe.orgs.ui.dialog.FormularioImagemDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigDecimal

class FormularioProdutoActivity : AppCompatActivity() {
    private val CADASTRAR_PRODUTO = "Cadastrar Produto"
    private val binding by lazy {
        ActivityFormularioProdutoBinding.inflate(layoutInflater)
    }
    private val produtoDAO by lazy {
        val db = AppDataBase.getInstance(this)
        db.produtoDaoRoom()
    }
    private var url: String? = null
    private var idProduto = 0L
    private var idUser = 0L
    private val scope = CoroutineScope(Dispatchers.IO)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title = CADASTRAR_PRODUTO
        configuraBotaoSalvar()
        configuraBotaoCarregarImagem()
        tentaCarregarProduto()
        tentaCarregarUser()
    }

    private fun tentaCarregarProduto() {
        idProduto = intent.getLongExtra(ConstanteActivities.CHAVE_PRODUTO_ID, 0L)
    }

    override fun onResume() {
        super.onResume()
        tentaBuscar()
    }

    private fun tentaCarregarUser() {
        idUser = intent.getLongExtra(ConstanteActivities.CHAVE_USER_ID, 0L)
    }

    private fun tentaBuscar() {
        lifecycleScope.launch {
            produtoDAO.buscaPorId(idProduto).collect { produto ->
                withContext(Dispatchers.Main) {
                    title = "Alterar produto"
                    preencheCampos(produto)
                }
            }
        }
    }

    private fun preencheCampos(produtoCarregado: Produto) {
        url = produtoCarregado.imagem
        binding.activityFormularioImagem.tentaCarregarImagem(produtoCarregado.imagem)
        binding.activityFormularioProdutoNome
            .setText(produtoCarregado.nome)
        binding.activityFormularioProdutoDescricao
            .setText(produtoCarregado.descricao)
        binding.activityFormularioProdutoValor.setText(produtoCarregado.valor.toPlainString())
    }

    private fun configuraBotaoCarregarImagem() {
        binding.activityFormularioImagem.setOnClickListener {
            FormularioImagemDialog(this).mostra(url) { imagem ->
                url = imagem
                binding.activityFormularioImagem.tentaCarregarImagem(url)
            }
        }
    }

    private fun configuraBotaoSalvar() {
        val button = binding.activityFormularioButton
        button.setOnClickListener(View.OnClickListener {
            val produtoNovo = criaProduto()
            scope.launch {
                produtoDAO.salva(produtoNovo) // salvando ou editando por meio do insert
                finish()
            }
        })
    }


    private fun criaProduto(): Produto {
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
            id = idProduto,
            nome = nome,
            descricao = descricao,
            valor = valor,
            imagem = url
        )
    }


}