package br.luizfilipe.orgs.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import br.luizfilipe.orgs.database.AppDataBase
import br.luizfilipe.orgs.databinding.ActivityLoginBinding
import br.luizfilipe.orgs.model.User
import br.luizfilipe.orgs.ui.activity.ConstanteActivities.Companion.CHAVE_USER_ID

class LoginActivity : Activity() {
    private val binding by lazy { // so e iniciada qunado acessada pela primeira vez
        ActivityLoginBinding.inflate(layoutInflater)
    }
    private val userDAORoom by lazy {
        val db = AppDataBase.getInstance(this)
        db.userDaoRoom()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraBotaoLogin()
        configuraBotaoCadastro()
    }

    private fun configuraBotaoLogin() {
        val buttonLogin = binding.activityLoginButton
        val campoUsername = binding.activityLoginUserNome
        val campoSenha = binding.activityLoginUserSenha
        buttonLogin.setOnClickListener(View.OnClickListener {
            val username = campoUsername.text.toString()
            val senha = campoSenha.text.toString()
            if (username.isNotEmpty() && senha.isNotEmpty()) {
                val user = userDAORoom.validaUser(username, senha)
                if (user != null) {
                    val idUserEncontrado = user.id
                    val intent = Intent(this, ListaProdutosActivity::class.java).apply {
                        putExtra(CHAVE_USER_ID, idUserEncontrado)
                    }
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Usuario nao encontrado", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(
                    this, "Insira as informacoes requeridas. Tente novamente",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun configuraBotaoCadastro() {
        val buttonCadastro = binding.activityLoginCadastrarUser
        buttonCadastro.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, CadastroUserActivity::class.java))
        })
    }

}