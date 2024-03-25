package br.luizfilipe.orgs.extensions

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

fun BigDecimal.formataParaMoedaBrasileira(): String{
    val formatador: NumberFormat = NumberFormat.getCurrencyInstance(Locale("pt","br"))
    return formatador.format(this)
}