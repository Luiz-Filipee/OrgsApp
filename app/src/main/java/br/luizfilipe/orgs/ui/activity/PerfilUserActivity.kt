package br.luizfilipe.orgs.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.luizfilipe.orgs.database.AppDataBase
import br.luizfilipe.orgs.databinding.ActivityPerfilUserBinding
import br.luizfilipe.orgs.model.User
import br.luizfilipe.orgs.ui.activity.ConstanteActivities.Companion.CHAVE_USER_ID

class PerfilUserActivity : AppCompatActivity() {
    private var idUser: Long = 0
    private val userDAORoom by lazy {
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
    }

    override fun onResume() {
        super.onResume()
        buscaUser()
    }

    private fun tentaCarregarUser() {
        idUser = intent.getLongExtra(CHAVE_USER_ID, 0L)
    }

    private fun buscaUser() {
        userDAORoom.buscaPorId(idUser)?.let {
            preencheCampos(it)
        }
    }

    private fun preencheCampos(user: User) {
        binding.activityPerfilUserCampoNome
            .setText(user.nome)
        binding.activityPerfilUserCampoEmail
            .setText(user.email)
        binding.activityPerfilUserCampoNome.isActivated
    }

}