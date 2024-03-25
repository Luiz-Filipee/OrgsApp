package br.luizfilipe.orgs.ui.activity

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import br.luizfilipe.orgs.R
import br.luizfilipe.orgs.databinding.ActivitySplashScreenBinding

class SplashScreen : Activity() {
    private val binding by lazy {
        ActivitySplashScreenBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val handler = Handler()
        handler.postDelayed({
            mostrarListaProdutosActivity()
        }, 2000)
    }

    private fun mostrarListaProdutosActivity() {
        val intent = Intent(this, ListaProdutosActivity::class.java)
        startActivity(intent)
        finish()
    }
}