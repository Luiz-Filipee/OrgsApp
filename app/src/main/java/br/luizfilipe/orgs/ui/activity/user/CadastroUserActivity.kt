package br.luizfilipe.orgs.ui.activity.user

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.luizfilipe.orgs.database.AppDataBase
import br.luizfilipe.orgs.databinding.ActivityCadastroUserBinding
import br.luizfilipe.orgs.extensions.toHash
import br.luizfilipe.orgs.model.User
import kotlinx.coroutines.launch

class CadastroUserActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityCadastroUserBinding.inflate(layoutInflater)
    }
    private val userDao by lazy {
        val db = AppDataBase.getInstance(this)
        db.userDaoRoom()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraBotaoSalvar()
        binding.cadastroUserClose.setOnClickListener(View.OnClickListener {
            finish()
        })
    }

    private fun configuraBotaoSalvar() {
        val buttonSalvar = binding.cadastroUserSalvar
        buttonSalvar.setOnClickListener(View.OnClickListener {
            val nome = binding.cadastroUserCampoNome.text.toString()
            val email = binding.cadastroUserCampoEmail.text.toString()
            val senha = binding.cadastroUserCampoSenha.text.toString()
            val telefone = binding.cadastroUserCampoTelefone.text.toString()

            if (validaInformacoes(nome, email, senha, telefone)) {
                val user = criaUser()
                try {
                    lifecycleScope.launch {
                        userDao.salva(user)
                        finish()
                    }
                } catch (e: Exception) {
                    Toast.makeText(
                        this, "Erro ao criar usuario",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }else{
                Toast.makeText(
                    this, "Todos os campos devem ser preenchidos",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

    }

    private fun validaInformacoes(
        nome: String,
        email: String,
        senha: String,
        telefone: String
    ) = nome.isNotEmpty() && email.isNotEmpty() && senha.isNotEmpty() && telefone.isNotEmpty()

    private fun criaUser(): User {
        val nome = binding.cadastroUserCampoNome.text.toString()
        val email = binding.cadastroUserCampoEmail.text.toString()
        val senha = binding.cadastroUserCampoSenha.text.toString().toHash()
        val telefone = binding.cadastroUserCampoTelefone.text.toString()

        return User(
            nome = nome,
            email = email,
            senha = senha,
            telefone = telefone
        )
    }

}