package br.luizfilipe.orgs.ui.activity

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import br.luizfilipe.orgs.database.AppDataBase
import br.luizfilipe.orgs.databinding.ActivityCadastroUserBinding
import br.luizfilipe.orgs.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class CadastroUserActivity : Activity() {
    private val CADASTRAR_USUARIO = "Cadastrar Usuario"
    private val binding by lazy {
        ActivityCadastroUserBinding.inflate(layoutInflater)
    }
    private val userDao by lazy {
        val db = AppDataBase.getInstance(this)
        db.userDaoRoom()
    }
    private var idUser: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = CADASTRAR_USUARIO
        setContentView(binding.root)
        configuraBotaoSalvar()
    }

    private fun configuraBotaoSalvar() {
        val buttonSalvar = binding.cadastroUserSalvar
        buttonSalvar.setOnClickListener(View.OnClickListener {
            val nome = binding.cadastroUserCampoNome.text.toString()
            val email = binding.cadastroUserCampoEmail.text.toString()
            val senha = binding.cadastroUserCampoSenha.text.toString()
            val telefone = binding.cadastroUserCampoTelefone.text.toString()

            if (nome.isNotEmpty() && email.isNotEmpty() && senha.isNotEmpty() && telefone.isNotEmpty()) {
                val user = criaUser()
                val scope = MainScope()
                try {
                    scope.launch(Dispatchers.IO) {
                        userDao.salva(user)
                        finish()
                    }
                } catch (e: Exception) {
                    Toast.makeText(
                        this, "Insira todas as informacoes corretas",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })

    }

    private fun criaUser(): User {
        val nome = binding.cadastroUserCampoNome.text.toString()
        val email = binding.cadastroUserCampoEmail.text.toString()
        val senha = binding.cadastroUserCampoSenha.text.toString()
        val telefone = binding.cadastroUserCampoTelefone.text.toString()

        return User(
            nome = nome,
            email = email,
            senha = senha,
            telefone = telefone
        )
    }

}