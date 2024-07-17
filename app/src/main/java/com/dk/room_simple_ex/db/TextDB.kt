package com.dk.room_simple_ex.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.dk.room_simple_ex.db.dao.TextDao
import com.dk.room_simple_ex.db.dao.TextDao2
import com.dk.room_simple_ex.db.entity.TextEntity
import com.dk.room_simple_ex.db.entity.TextEntity2

@Database(
    entities = [TextEntity::class, TextEntity2::class],
    version = 3
)
abstract class TextDatabase : RoomDatabase(){

    abstract fun textDao() : TextDao
    abstract fun textDao2() : TextDao2

    companion object {

        @Volatile
        private var INSTANCE : TextDatabase? = null

        fun getDatabase(
            context: Context
        ) : TextDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TextDatabase::class.java,
                    "text_database"
                )
                    //.fallbackToDestructiveMigration()
                    .addMigrations(MIGRATION_1_2)
                    .addMigrations(MIGRATION_2_3)
                    .build()
                INSTANCE = instance
                instance
            }
        }

        val MIGRATION_1_2 = object : Migration(1,2) {
            override fun migrate(database: SupportSQLiteDatabase) {

                Log.d("migrate", "MIGRATION_1_2")

                database.execSQL("CREATE TABLE `text_table2` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `text2` TEXT NOT NULL)")
            }

        }

        val MIGRATION_2_3 = object : Migration(2,3) {
            override fun migrate(database: SupportSQLiteDatabase) {

                Log.d("migrate", "MIGRATION_2_3")

                database.execSQL("ALTER TABLE `text_table2` ADD COLUMN `newText` TEXT NOT NULL DEFAULT `default_new_txt`")
            }

        }

    }




}