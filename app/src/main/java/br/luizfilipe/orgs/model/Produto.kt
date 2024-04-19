package br.luizfilipe.orgs.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Entity(foreignKeys = [ForeignKey(entity = User::class,
    parentColumns = ["id"],
    childColumns = ["userForeignKey"],
    onDelete = ForeignKey.CASCADE)])
@Parcelize
data class Produto(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    var nome: String,
    var descricao: String,
    var valor: BigDecimal,
    var imagem: String? = null,
    val userForeignKey: Long? = null
): Parcelable{

}