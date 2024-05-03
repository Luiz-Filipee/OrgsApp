package br.luizfilipe.orgs.ui.activity.user

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.luizfilipe.orgs.database.AppDataBase
import br.luizfilipe.orgs.databinding.ActivityCadastroUserBinding
import br.luizfilipe.orgs.extensions.toHash
import br.luizfilipe.orgs.extensions.toast
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
        binding.cadastroUserSalvar.setOnClickListener(View.OnClickListener {
            val novoUsuario = criaUser()
            lifecycleScope.launch {
                cadastra(novoUsuario)
            }
        })
    }

    private fun cadastra(usuario: User) {
        lifecycleScope.launch {
            try {
                userDao.salva(usuario)
                finish()
            } catch (e: Exception) {
                Log.e("CadastroUsuario", "configuraBotaoSalvar: ", e)
                toast("Falha ao cadastrar usuario.")
            }
        }
    }

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