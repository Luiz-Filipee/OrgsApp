package br.luizfilipe.orgs.di.modules

import android.content.Context
import androidx.room.Room
import br.luizfilipe.orgs.data.database.MIGRATION_3_4
import org.koin.dsl.module
import br.luizfilipe.orgs.data.database.AppDataBase as AppDataBase

val dataBaseModule = module {

    single { providerAppDataBase(get()) }
    single { get<AppDataBase>().userDaoRoom() }
    single { get<AppDataBase>().produtoDaoRoom() }

}

fun providerAppDataBase(context: Context): AppDataBase {
    return Room.databaseBuilder(
        context,
        AppDataBase::class.java,
        "br.luizfilipe.orgs"
    ).addMigrations(MIGRATION_3_4)
        .build()
}

