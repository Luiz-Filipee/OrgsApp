package br.luizfilipe.orgs.view.activity.user

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import br.luizfilipe.orgs.R
import br.luizfilipe.orgs.databinding.ActivityPerfilUserBinding
import br.luizfilipe.orgs.extensions.vaiPara
import br.luizfilipe.orgs.preferences.dataStore
import br.luizfilipe.orgs.preferences.usuarioLogadoPreferences
import br.luizfilipe.orgs.view.activity.UsuarioBaseActivity
import br.luizfilipe.orgs.viewmodel.UserViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PerfilUserActivity : UsuarioBaseActivity() {

    private val userViewModel: UserViewModel by viewModel()

    private val binding by lazy {
        ActivityPerfilUserBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        tentaCarregarUser()
        menuNavegacaoPerfilUser()
        inicializaCampos()
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
        userViewModel.buscaPorId(usuarioId).collect { user ->
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

    private fun inicializaCampos() {

        binding.perfilUserVoltar.setOnClickListener(View.OnClickListener {
            finish()
        })

        binding.perfilUserOpcoes.setOnClickListener(View.OnClickListener {
            vaiPara(OptionsUserActivity::class.java)
        })
    }

}