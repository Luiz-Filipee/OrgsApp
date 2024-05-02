package br.luizfilipe.orgs.ui.activity.user

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import br.luizfilipe.orgs.database.AppDataBase
import br.luizfilipe.orgs.databinding.ActivityForgotPasswordBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class ActivityForgotPassword : Activity() {
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
    }

    private fun buscaUsuarioEmail() {
        val buttonEnviar = binding.activityForgotUserButton
        buttonEnviar.setOnClickListener(View.OnClickListener {
            val email = binding.activityForgotUserCampoEmail.text.toString()
            MainScope().launch(Dispatchers.IO) {
                val user = userDao.buscaUserPorEmail(email)
                if (user != null) {
                    Toast.makeText(
                        this@ActivityForgotPassword,
                        "usuario encontrado",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }
}