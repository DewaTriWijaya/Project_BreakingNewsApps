package com.project.mycode.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.project.mycode.data.local.NewEarthquakeDB
import com.project.mycode.data.local.FeltEarthquakeDB
import com.project.mycode.data.local.ResultDao

@Database(
    entities = [NewEarthquakeDB::class, FeltEarthquakeDB::class],
    version = 1,
    exportSchema = false
)
abstract class ResultDatabase : RoomDatabase() {

    abstract fun resultDao(): ResultDao

    companion object {
        @Volatile
        private var INSTANCE: ResultDatabase? = null

        @Synchronized
        fun getInstance(context: Context): ResultDatabase {
            val instanceA = INSTANCE
            if (instanceA != null) {
                return instanceA
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ResultDatabase::class.java,
                    "result_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}