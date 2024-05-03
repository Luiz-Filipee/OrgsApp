package br.luizfilipe.orgs.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

// Todo Todas as alteracoes que fizermos dentro do banco de dados devemos informar por meio
// Todo das migrations, independente do que for

// Criando uma migration para criar a table de user
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `User` (
            `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
            `nome` TEXT NOT NULL, `email` TEXT NOT NULL, 
            `senha` TEXT NOT NULL, `telefone` TEXT NOT NULL, 
            `imagem` TEXT)"""
        )
    }

}

val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE Produto ADD COLUMN 'usuarioId' TEXT")
    }

}