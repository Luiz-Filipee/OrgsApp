package br.luizfilipe.orgs.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import br.luizfilipe.orgs.R

class MainActivity : AppCompatActivity(R.layout.activity_main){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*val titulo = findViewById<TextView>(R.id.activity_main_titulo)
        val descricao = findViewById<TextView>(R.id.activity_main_descricao)
        val valor = findViewById<TextView>(R.id.activity_main_valor)
        titulo.setText("Salada de frutas")
        descricao.setText("Laranja, manga e uva")
        valor.setText("19,99")
        */
        val recyclerView = findViewById<RecyclerView>(R.id.activity_main_recyclerview)

    }

}