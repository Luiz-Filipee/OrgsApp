package br.luizfilipe.orgs.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import br.luizfilipe.orgs.R
import br.luizfilipe.orgs.database.AppDataBase
import br.luizfilipe.orgs.databinding.ActivityPerfilUserBinding
import br.luizfilipe.orgs.model.User
import br.luizfilipe.orgs.ui.activity.ConstanteActivities.Companion.CHAVE_USER_ID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class PerfilUserActivity : Activity() {
    private var idUser: Long = 0
    private val userDAORoom by lazy {
        val db = AppDataBase.getInstance(this)
        db.userDaoRoom()
    }
    private var userId: Long = 0

    private val binding by lazy {
        ActivityPerfilUserBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        tentaCarregarUser()
    }

    override fun onResume() {
        super.onResume()
        buscaUser()
    }

    private fun tentaCarregarUser() {
        idUser = intent.getLongExtra(CHAVE_USER_ID, 0L)
    }

    private fun buscaUser() {
        val scope = MainScope()
        scope.launch(Dispatchers.IO) {
            userDAORoom.buscaPorId(idUser)?.let {
                preencheCampos(it)
            }
        }
    }

    private fun menunavigation() {
        val buttonNavigation = binding.bottomNavigation
        buttonNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_edita -> {
                    abreTelaEditaUser()
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.item_home -> {
                    voltaPraListaActivity()
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.item_logout -> {
                    logoutConta()
                    return@setOnNavigationItemSelectedListener true
                }


                else -> return@setOnNavigationItemSelectedListener false
            }
        }
    }

    private fun abreTelaEditaUser(){
        val intent = Intent(this, ActivtyEditaUser::class.java).apply {
            putExtra(CHAVE_USER_ID, userId)
        }
        startActivity(intent)
    }

    private fun voltaPraListaActivity() {
        startActivity(Intent(this, ListaProdutosActivity::class.java))
    }

    private fun logoutConta() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun preencheCampos(user: User) {
        binding.cadastroNomeUser
            .setText(user.nome)
        binding.cadastroDescricaoUser
            .setText("")
    }

}