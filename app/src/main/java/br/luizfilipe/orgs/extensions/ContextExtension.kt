package br.luizfilipe.orgs.extensions

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import br.luizfilipe.orgs.preferences.dataStore
import br.luizfilipe.orgs.preferences.usuarioLogadoPreferences
import kotlinx.coroutines.CoroutineScope

fun Context.vaiPara(classe: Class<*>, intent: Intent.() -> Unit = {}) {
    Intent(this, classe)
        .apply {
            intent()
            startActivity(this)
        }
}

fun Context.toast(message: String){
    Toast.makeText(
        this,
        message,
        Toast.LENGTH_SHORT
    ).show()
}
