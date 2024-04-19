package br.luizfilipe.orgs.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import br.luizfilipe.orgs.R
import br.luizfilipe.orgs.database.AppDataBase
import br.luizfilipe.orgs.databinding.ActivityCadastroUserBinding
import br.luizfilipe.orgs.extensions.tentaCarregarImagem
import br.luizfilipe.orgs.model.User
import br.luizfilipe.orgs.ui.dialog.FormularioImagemDialog

class CadastroUserActivity : AppCompatActivity() {
    private val CADASTRAR_USUARIO = "Cadastrar Usuario"
    private val binding by lazy {
        ActivityCadastroUserBinding.inflate(layoutInflater)
    }
    private val userDao by lazy {
        val db = AppDataBase.getInstance(this)
        db.userDaoRoom()
    }
    private var url: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = CADASTRAR_USUARIO
        setContentView(binding.root)
        configuraBotaoCarregarImagem()
        configuraBotaoSalvar()
    }

    private fun configuraBotaoSalvar(){
        val buttonSalvar = binding.activityCadastroButton
        buttonSalvar.setOnClickListener(View.OnClickListener {
            val nome = binding.activityCadastroNomeCampo.text.toString()
            val email = binding.activityCadastroEmailCampo.text.toString()
            val senha = binding.activityCadastroSenhaCampo.text.toString()

            if(nome.isNotEmpty() && email.isNotEmpty() && senha.isNotEmpty()){
                val user = criaUser()
                userDao.salva(user)
                Toast.makeText(this, "Usuario cadastrado com sucesso ${user.toString()}",
                    Toast.LENGTH_SHORT).show()
                finish()
            }else{
                Toast.makeText(this, "Insira todas as informacoes corretas",
                    Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun criaUser(): User {
        val nome = binding.activityCadastroNomeCampo.text.toString()
        val email = binding.activityCadastroEmailCampo.text.toString()
        val senha = binding.activityCadastroSenhaCampo.text.toString()

        return User(
            nome = nome,
            email = email,
            senha = senha
        )
    }

    private fun configuraBotaoCarregarImagem() {
        binding.activityCadastroImagemBotao.setOnClickListener {
            FormularioImagemDialog(this).mostra(url) { imagem ->
                url = imagem
                binding.activityCadastroImagem.tentaCarregarImagem(url)
            }
        }
    }

}