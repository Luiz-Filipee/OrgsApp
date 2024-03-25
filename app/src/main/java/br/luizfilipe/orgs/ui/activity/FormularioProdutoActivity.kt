package br.luizfilipe.orgs.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import br.luizfilipe.orgs.database.AppDataBase
import br.luizfilipe.orgs.databinding.ActivityFormularioProdutoBinding
import br.luizfilipe.orgs.extensions.tentaCarregarImagem
import br.luizfilipe.orgs.model.Produto
import br.luizfilipe.orgs.ui.dialog.FormularioImagemDialog
import java.math.BigDecimal

private const val CADASTRAR_PRODUTO = "Cadastrar produto"

class FormularioProdutoActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityFormularioProdutoBinding.inflate(layoutInflater)
    }
    private var url: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title = CADASTRAR_PRODUTO
        configuraBotaoSalvar()
        configuraBotaoCarregarImagem()
        supportActionBar
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
        val db = AppDataBase.getInstance(this)
        val produtoDaoRoom = db.produtoDaoRoom()
        button.setOnClickListener(View.OnClickListener {
            val produtoNovo = criaProduto()
            produtoDaoRoom.salva(produtoNovo)
            finish()
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
            nome = nome,
            descricao = descricao,
            valor = valor,
            imagem = url
        )
    }


}