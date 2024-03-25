package br.luizfilipe.orgs.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.luizfilipe.orgs.database.converter.Converters
import br.luizfilipe.orgs.database.dao.ProdutoDAORoom
import br.luizfilipe.orgs.model.Produto

@Database(entities = [Produto::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun produtoDaoRoom(): ProdutoDAORoom

    companion object{
        fun getInstance(context: Context): AppDataBase{
            return Room.databaseBuilder(
                context,
                AppDataBase::class.java,
                "orgs.db"
            ).allowMainThreadQueries()
                .build()
        }
    }
}