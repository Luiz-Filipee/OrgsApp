package br.luizfilipe.orgs.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import br.luizfilipe.orgs.database.AppDataBase
import br.luizfilipe.orgs.databinding.ActivityLoginBinding
import br.luizfilipe.orgs.ui.activity.ConstanteActivities.Companion.CHAVE_USER_ID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
        configuraBotaoEsqueceuSenha()
        binding.activityLoginUserNome.setText("Q")
        binding.activityLoginUserSenha.setText("q")
    }

    private fun configuraBotaoLogin() {
        val buttonLogin = binding.activityLoginButton
        val campoUsername = binding.activityLoginUserNome
        val campoSenha = binding.activityLoginUserSenha
        buttonLogin.setOnClickListener(View.OnClickListener {
            val username = campoUsername.text.toString()
            val senha = campoSenha.text.toString()
            if (username.isNotEmpty() && senha.isNotEmpty()) {
                val scope = CoroutineScope(Dispatchers.IO)
                scope.launch {
                    val user = userDAORoom.validaUser(username, senha)
                    withContext(Dispatchers.Main) {
                        if (user != null) {
                            val idUserEncontrado = user.id
                            val intent = Intent(
                                this@LoginActivity,
                                ListaProdutosActivity::class.java
                            ).apply {
                                putExtra(CHAVE_USER_ID, idUserEncontrado)
                            }
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(
                                this@LoginActivity,
                                "Usuario nao encontrado",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
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

    private fun configuraBotaoEsqueceuSenha(){
        val buttonEsqueceuSenha = binding.activityLoginForgotPasswordUser
        buttonEsqueceuSenha.setOnClickListener(View.OnClickListener {

        })
    }

}