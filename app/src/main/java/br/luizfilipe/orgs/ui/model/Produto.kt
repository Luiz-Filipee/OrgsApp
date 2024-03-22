package br.luizfilipe.orgs.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Parcelize
data class Produto(
    var nome: String,
    var descricao: String,
    var valor: BigDecimal,
    var imagem: String? = null,
): Parcelable