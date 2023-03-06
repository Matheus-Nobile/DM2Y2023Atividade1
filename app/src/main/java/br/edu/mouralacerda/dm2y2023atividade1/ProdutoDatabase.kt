package br.edu.mouralacerda.dm2y2023atividade1

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Produto::class], version = 1)
abstract class ProdutoDatabase : RoomDatabase() {

    abstract fun produtoDao(): ProdutoDAO

    companion object {

        private var database: ProdutoDatabase? = null
        private val DATABASE = "produtoDB"

        fun getInstance(context: Context): ProdutoDatabase {

            if (database == null)
                database = criaBanco(context)

            return database!!
        }

        private fun criaBanco(context: Context): ProdutoDatabase {
            return Room.databaseBuilder(context, ProdutoDatabase::class.java, DATABASE)
                .allowMainThreadQueries()
                .build()
        }
    }
}