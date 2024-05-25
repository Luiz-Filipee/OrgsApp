package br.luizfilipe.orgs.view.activity.user

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.luizfilipe.orgs.data.model.User
import br.luizfilipe.orgs.databinding.ActivityCadastroUserBinding
import br.luizfilipe.orgs.extensions.toHash
import br.luizfilipe.orgs.extensions.toast
import br.luizfilipe.orgs.viewmodel.UserViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CadastroUserActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCadastroUserBinding.inflate(layoutInflater)
    }

    private val userViewModel: UserViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        inicializaCampos()
    }

    private fun inicializaCampos() {
        binding.cadastroUserClose.setOnClickListener(View.OnClickListener {
            finish()
        })
        binding.cadastroUserSalvar.setOnClickListener(View.OnClickListener {
            val novoUsuario = criaUser()
            cadastraUser(novoUsuario)
        })
    }

    private fun cadastraUser(novoUsuario: User) {
        lifecycleScope.launch {
            try {
                val userExiste = userViewModel.buscaUserPorEmail(novoUsuario.email)
                Log.d("CadastroUser", "User existe: $userExiste")
                if (userExiste) {
                    toast("Este usuario ja existe")
                } else {
                    Log.d("CadastroUser", "Cadastrando usuário: $novoUsuario")
                    userViewModel.cadastrar(novoUsuario).join()
                    toast("Usuario cadastrado com sucesso")
                    finish()
                    Log.i("CadastroUser", "Usuario cadastrado com sucesso: $novoUsuario")
                }
            } catch (e: Exception) {
                Log.e("CadastroUser", "Erro ao cadastrar usuario", e)
                toast("Erro ao cadastrar usuario")
            }
        }
    }

    private fun criaUser(): User {
        val nome = binding.cadastroUserCampoNome.text.toString()
        val email = binding.cadastroUserCampoEmail.text.toString()
        val senha = binding.cadastroUserCampoSenha.text.toString().toHash()
        val telefone = binding.cadastroUserCampoTelefone.text.toString()

        return User(
            nome = nome,
            email = email,
            senha = senha,
            telefone = telefone
        )
    }

}