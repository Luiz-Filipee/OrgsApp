package br.luizfilipe.orgs.ui.activity

import android.app.Activity
import android.os.Bundle
import br.luizfilipe.orgs.database.AppDataBase
import br.luizfilipe.orgs.databinding.ActivityEditaUserBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class ActivtyEditaUser : Activity() {
    private val binding by lazy {
        ActivityEditaUserBinding.inflate(layoutInflater)
    }
    private val userDao by lazy {
        val db = AppDataBase.getInstance(this)
        db.userDaoRoom()
    }
    private var idUser: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        tentaCarregarUser()
    }

    private fun tentaCarregarUser() {
        idUser = intent.getLongExtra(ConstanteActivities.CHAVE_USER_ID, 0L)
        MainScope().launch(Dispatchers.IO) {
            userDao.buscaPorId(idUser)?.let {
                MainScope().launch(Dispatchers.Main) {
                    binding.activityEditaUserCampoNome.setText(it.nome)
                    binding.activityEditaUserCampoBio.setText("")
                }
            }
        }
    }
}