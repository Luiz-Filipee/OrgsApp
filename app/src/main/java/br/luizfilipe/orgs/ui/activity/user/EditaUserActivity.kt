package br.luizfilipe.orgs.ui.activity.user

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.luizfilipe.orgs.database.AppDataBase
import br.luizfilipe.orgs.databinding.ActivityEditaUserBinding
import br.luizfilipe.orgs.preferences.dataStore
import br.luizfilipe.orgs.preferences.usuarioLogadoPreferences
import kotlinx.coroutines.launch

class EditaUserActivity : AppCompatActivity() {
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
        lifecycleScope.launch {
            dataStore.data.collect { preferences ->
                preferences[usuarioLogadoPreferences]?.let { usuarioId ->
                    userDao.buscaPorId(usuarioId).collect { user ->
                        binding.activityEditaUserCampoNome.setText(user.nome)
                        binding.activityEditaUserCampoEmail.setText(user.email)
                    }
                }
            }
        }
    }

}