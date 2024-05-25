package br.luizfilipe.orgs.view.activity.user

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.luizfilipe.orgs.data.database.AppDataBase
import br.luizfilipe.orgs.databinding.ActivityEditaUserBinding
import br.luizfilipe.orgs.preferences.dataStore
import br.luizfilipe.orgs.preferences.usuarioLogadoPreferences
import br.luizfilipe.orgs.viewmodel.UserViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditaUserActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityEditaUserBinding.inflate(layoutInflater)
    }

    private val userViewModel: UserViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        tentaCarregarUser()
    }

    private fun tentaCarregarUser() {
        lifecycleScope.launch {
            dataStore.data.collect { preferences ->
                preferences[usuarioLogadoPreferences]?.let { usuarioId ->
                    userViewModel.buscaPorId(usuarioId).collect { user ->
                        binding.activityEditaUserCampoNome.setText(user.nome)
                        binding.activityEditaUserCampoEmail.setText(user.email)
                    }
                }
            }
        }
    }

}