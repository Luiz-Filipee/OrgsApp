package br.luizfilipe.orgs.ui.activity.user

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.luizfilipe.orgs.database.AppDataBase
import br.luizfilipe.orgs.databinding.ActivityForgotPasswordBinding
import kotlinx.coroutines.launch

class RecuperaSenhaActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityForgotPasswordBinding.inflate(layoutInflater)
    }
    private val userDao by lazy {
        AppDataBase.getInstance(this).userDaoRoom()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        buscaUsuarioEmail()
        binding.activityForgotUserClose.setOnClickListener(View.OnClickListener {
            finish()
        })
    }

    private fun buscaUsuarioEmail() {
        val buttonEnviar = binding.activityForgotUserButton
        buttonEnviar.setOnClickListener(View.OnClickListener {
            val email = binding.activityForgotUserCampoEmail.text.toString()
            lifecycleScope.launch {
                userDao.buscaUserPorEmail(email)?.let { user ->
                    Toast.makeText(
                        this@RecuperaSenhaActivity,
                        "Foi enviado um link no seu email",
                        Toast.LENGTH_SHORT
                    ).show()
                } ?: Toast.makeText(
                    this@RecuperaSenhaActivity,
                    "Usuario nao encontrado" ,
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}