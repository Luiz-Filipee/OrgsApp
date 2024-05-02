package br.luizfilipe.orgs.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.concurrent.Flow

@Entity
@Parcelize
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    var nome: String,
    var email: String,
    var senha: String,
    var telefone: String,
    var imagem: String? = null,
) : Parcelable {
    override fun toString(): String {
        return "User(nome='$nome', email='$email', senha='$senha')"
    }
}