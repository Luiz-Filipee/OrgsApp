package br.luizfilipe.orgs.ui.activity

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import br.luizfilipe.orgs.databinding.ActivitySplashScreenBinding
import br.luizfilipe.orgs.extensions.vaiPara
import br.luizfilipe.orgs.ui.activity.produto.ListaProdutosActivity
import br.luizfilipe.orgs.ui.activity.user.LoginActivity

class SplashScreen : Activity() {
    private val binding by lazy {
        ActivitySplashScreenBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val handler = Handler()
        handler.postDelayed({
            vaiPara(ListaProdutosActivity::class.java).apply {
                finish()
            }
        }, 2000)
    }
}