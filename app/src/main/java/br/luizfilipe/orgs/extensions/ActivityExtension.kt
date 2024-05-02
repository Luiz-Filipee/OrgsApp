package br.luizfilipe.orgs.extensions

import android.content.Context
import android.content.Intent

fun Context.vaiPara(classe: Class<*>, intent: Intent.() -> Unit = {}) {
    Intent(this, classe)
        .apply {
            intent()
            startActivity(this)
        }
}
