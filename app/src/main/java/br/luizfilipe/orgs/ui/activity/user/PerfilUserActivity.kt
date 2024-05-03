package br.luizfilipe.orgs.ui.activity.user

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import br.luizfilipe.orgs.R
import br.luizfilipe.orgs.database.AppDataBase
import br.luizfilipe.orgs.databinding.ActivityPerfilUserBinding
import br.luizfilipe.orgs.extensions.vaiPara
import br.luizfilipe.orgs.preferences.dataStore
import br.luizfilipe.orgs.preferences.usuarioLogadoPreferences
import br.luizfilipe.orgs.ui.activity.UsuarioBaseActivity
import kotlinx.coroutines.launch

class PerfilUserActivity : UsuarioBaseActivity() {
    private val userDao by lazy {
        val db = AppDataBase.getInstance(this)
        db.userDaoRoom()
    }

    private val binding by lazy {
        ActivityPerfilUserBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        tentaCarregarUser()
        menuNavegacaoPerfilUser()
        configuraBotaoVoltar()
        configuraBotaoOpcoes()
    }

    private fun tentaCarregarUser() {
        lifecycleScope.launch {
            dataStore.data.collect { preferences ->
                preferences[usuarioLogadoPreferences]?.let { usuarioId ->
                    preencheCampos(usuarioId)
                }
            }
        }
    }

    private suspend fun preencheCampos(usuarioId: Long) {
        userDao.buscaPorId(usuarioId).collect { user ->
            binding.perfilNomeUser.text = user.nome
            binding.perfilEmailUser.text = user.email
        }
    }


    private fun menuNavegacaoPerfilUser() {
        val buttonNavigation = binding.bottomNavigation
        buttonNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_edita -> {
                    vaiPara(EditaUserActivity::class.java)
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.item_home -> {
                    finish()
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.item_logout -> {
                    lifecycleScope.launch {
                        deslogaUsuario()
                    }
                    return@setOnNavigationItemSelectedListener true
                }

                else -> return@setOnNavigationItemSelectedListener false
            }
        }
    }

    private fun configuraBotaoVoltar() {
        binding.perfilUserVoltar.setOnClickListener(View.OnClickListener {
            finish()
        })
    }

    private fun configuraBotaoOpcoes() {
        binding.perfilUserOpcoes.setOnClickListener(View.OnClickListener {

        })
    }

}