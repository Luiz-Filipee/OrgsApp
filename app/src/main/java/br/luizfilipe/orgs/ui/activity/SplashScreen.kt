package br.luizfilipe.orgs.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import br.luizfilipe.orgs.databinding.ActivitySplashScreenBinding
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
            mostrarLoginActivity()
        }, 2000)
    }

    private fun mostrarLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}