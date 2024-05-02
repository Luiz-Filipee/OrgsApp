package br.luizfilipe.orgs.ui.activity.user

import android.app.Activity
import android.os.Bundle
import br.luizfilipe.orgs.R
import br.luizfilipe.orgs.database.AppDataBase
import br.luizfilipe.orgs.databinding.ActivityPerfilUserBinding
import br.luizfilipe.orgs.extensions.vaiPara
import br.luizfilipe.orgs.ui.activity.ConstanteActivities.Companion.CHAVE_USER_ID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class PerfilUserActivity : Activity() {
    private val userDao by lazy {
        val db = AppDataBase.getInstance(this)
        db.userDaoRoom()
    }

    private val binding by lazy {
        ActivityPerfilUserBinding.inflate(layoutInflater)
    }
    private var idUser: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        tentaCarregarUser()
        menunavigation()
    }

    private fun tentaCarregarUser() {
        MainScope().launch(Dispatchers.IO) {
            intent.getLongExtra(CHAVE_USER_ID, 0L).let { usuarioId ->
                userDao.buscaPorId(usuarioId).collect { user ->
                    binding.perfilNomeUser.setText(user.nome)
                    binding.perfilBioUser.setText("Meu dia ta incrivel")
                    idUser = user.id
                }
            }
        }
    }


    private fun menunavigation() {
        val buttonNavigation = binding.bottomNavigation
        buttonNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_edita -> {
                    vaiPara(ActivtyEditaUser::class.java) {
                        putExtra(CHAVE_USER_ID, idUser)
                    }
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.item_home -> {
                    finish()
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.item_logout -> {
                    vaiPara(LoginActivity::class.java)
                    return@setOnNavigationItemSelectedListener true
                }

                else -> return@setOnNavigationItemSelectedListener false
            }
        }
    }
}