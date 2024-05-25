package br.luizfilipe.orgs.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

@Dao
interface InterfaceDataSource<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun salva(o: T)

    @Delete
    suspend fun remove(o: T)

    @Update
    suspend fun update(o: T)

}