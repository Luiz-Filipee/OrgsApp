package br.luizfilipe.orgs.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.luizfilipe.orgs.database.converter.Converters
import br.luizfilipe.orgs.database.dao.ProdutoDAORoom
import br.luizfilipe.orgs.database.dao.UserDAORoom
import br.luizfilipe.orgs.model.Produto
import br.luizfilipe.orgs.model.User

@Database(
    entities = [
        Produto::class,
        User::class],
    version = 4,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {

    abstract fun produtoDaoRoom(): ProdutoDAORoom
    abstract fun userDaoRoom(): UserDAORoom


    companion object {
        @Volatile
        private lateinit var db: AppDataBase
        fun getInstance(context: Context): AppDataBase {
            if (::db.isInitialized) return db
            return Room.databaseBuilder(
                context,
                AppDataBase::class.java,
                "br.luizfilipe.orgs"
            ).addMigrations(MIGRATION_3_4)
                .build().also {
                    db = it
                }
        }
    }

//    companion object {
//        fun getInstance(context: Context): AppDataBase {
//            return db ?: Room.databaseBuilder(
//                context,
//                AppDataBase::class.java,
//                "orgs.db"
//            ).addMigrations(MIGRATION_3_4)
//                .build().also {
//                    db
//                }
//        }
//    }
}