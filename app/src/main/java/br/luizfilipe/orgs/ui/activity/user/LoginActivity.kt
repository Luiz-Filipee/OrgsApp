package br.luizfilipe.orgs.ui.activity.user

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import br.luizfilipe.orgs.database.AppDataBase
import br.luizfilipe.orgs.databinding.ActivityLoginBinding
import br.luizfilipe.orgs.extensions.toHash
import br.luizfilipe.orgs.extensions.vaiPara
import br.luizfilipe.orgs.preferences.dataStore
import br.luizfilipe.orgs.preferences.usuarioLogadoPreferences
import br.luizfilipe.orgs.ui.activity.produto.ListaProdutosActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private val binding by lazy { // so e iniciada qunado acessada pela primeira vez
        ActivityLoginBinding.inflate(layoutInflater)
    }
    private val userDAO by lazy {
        AppDataBase.getInstance(this).userDaoRoom()
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
        binding.activityLoginButton.setOnClickListener {
            val usuario = binding.activityLoginUserNome.text.toString()
            val senha = binding.activityLoginUserSenha.text.toString().toHash()
            lifecycleScope.launch {
                userDAO.validaUser(usuario, senha)?.let { usuario ->
                    dataStore.edit { preferences ->
                        Log.i("ListaProdutos", "configuraBotaoLogin: $usuario")
                        preferences[usuarioLogadoPreferences] = usuario.id
                    }
                    vaiPara(ListaProdutosActivity::class.java)
                    finish()
                } ?: Toast.makeText(
                    this@LoginActivity,
                    "Falha na autenticacao",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun configuraBotaoCadastro() {
        binding.activityLoginCadastrarUser.setOnClickListener(View.OnClickListener {
            vaiPara(CadastroUserActivity::class.java)
        })
    }

    private fun configuraBotaoEsqueceuSenha() {
        binding.activityLoginForgotPasswordUser.setOnClickListener(View.OnClickListener {
            vaiPara(ActivityForgotPassword::class.java)
        })
    }
}
