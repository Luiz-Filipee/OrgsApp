package br.luizfilipe.orgs.ui.activity.user

import android.app.Activity
import android.os.Bundle
import br.luizfilipe.orgs.database.AppDataBase
import br.luizfilipe.orgs.databinding.ActivityEditaUserBinding
import br.luizfilipe.orgs.ui.activity.ConstanteActivities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ActivtyEditaUser : Activity() {
    private val binding by lazy {
        ActivityEditaUserBinding.inflate(layoutInflater)
    }
    private val userDao by lazy {
        val db = AppDataBase.getInstance(this)
        db.userDaoRoom()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        tentaCarregarUser()
    }

    private fun tentaCarregarUser() {
        MainScope().launch(Dispatchers.IO) {
            intent.getLongExtra(ConstanteActivities.CHAVE_USER_ID, 0L).let { usuarioId ->
                userDao.buscaPorId(usuarioId).collect { user ->
                    binding.activityEditaUserCampoNome.setText(user.nome)
                    binding.activityEditaUserCampoEmail.setText(user.email)
                    userDao.salva(user)
                }
            }
        }
    }


}