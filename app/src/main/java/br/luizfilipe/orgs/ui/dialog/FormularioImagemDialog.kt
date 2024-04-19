package br.luizfilipe.orgs.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import br.luizfilipe.orgs.databinding.ActivityFormularioImagemBinding
import br.luizfilipe.orgs.extensions.tentaCarregarImagem
import com.google.android.material.snackbar.Snackbar

class FormularioImagemDialog(private val context: Context) {

    fun mostra(
        urlPadrao: String? = null,
        quandoImagemCarregada: (imagem: String) -> Unit
    ) {
        ActivityFormularioImagemBinding
            .inflate(LayoutInflater.from(context)).apply {

                urlPadrao.let {
                    formularioImagemImageview.tentaCarregarImagem(it)
                    formularioImagemEdittext.setText(it)
                }

                formularioImagemBotaoCarregar.setOnClickListener {
                    val url = formularioImagemEdittext.text.toString()
                    formularioImagemImageview.tentaCarregarImagem(url)
                }

                AlertDialog.Builder(context)
                    .setView(root)
                    .setPositiveButton("Confirmar") { _, _ ->
                        val url = formularioImagemEdittext.text.toString()
                        quandoImagemCarregada(url)
                    }
                    .setNegativeButton("Cancela") { _, _ ->

                    }
                    .show()

            }

    }

}