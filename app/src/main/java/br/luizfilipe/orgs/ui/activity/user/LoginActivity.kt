import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import br.luizfilipe.orgs.database.AppDataBase
import br.luizfilipe.orgs.databinding.ActivityLoginBinding
import br.luizfilipe.orgs.extensions.vaiPara
import br.luizfilipe.orgs.model.User
import br.luizfilipe.orgs.ui.activity.ConstanteActivities
import br.luizfilipe.orgs.ui.activity.ConstanteActivities.Companion.CHAVE_USER_ID
import br.luizfilipe.orgs.ui.activity.produto.ListaProdutosActivity
import br.luizfilipe.orgs.ui.activity.user.ActivityForgotPassword
import br.luizfilipe.orgs.ui.activity.user.CadastroUserActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : Activity() {
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
        binding.activityLoginButton.setOnClickListener(View.OnClickListener {
            val usuario = binding.activityLoginUserNome.text.toString()
            val senha = binding.activityLoginUserSenha.text.toString()
            MainScope().launch(Dispatchers.IO){
                userDAO.validaUser(usuario,senha)?.let { usuario ->
                    vaiPara(ListaProdutosActivity::class.java){
                        putExtra(CHAVE_USER_ID, usuario.id)
                    }
                } ?: Toast.makeText(
                    this@LoginActivity,
                    "Falha na autenticacao",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun configuraBotaoCadastro(){
        vaiPara(CadastroUserActivity::class.java)
    }

    private fun configuraBotaoEsqueceuSenha(){
        vaiPara(ActivityForgotPassword::class.java)
    }
}
