package com.vs.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vs.dao.UserDao
import com.vs.models.User

/**
 * Created By Sachin
 */
@Database(entities = arrayOf(User::class), version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {

    abstract fun notesDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase {
            if (INSTANCE != null) {
                return INSTANCE!!
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context,
                        UserDatabase::class.java,
                        "user_database"
                ).build()
                INSTANCE = instance
                return instance
            }

        }
    }
}