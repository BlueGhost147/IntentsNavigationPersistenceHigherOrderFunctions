package database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import beans.Pet
import beans.Student

@Database(entities = [Student::class, Pet::class], version = 3)
abstract class StudentsRoomDatabase : RoomDatabase() {

    abstract val studentDao: StudentDao
    abstract val petDao: PetDao

    companion object {
        private var INSTANCE: StudentsRoomDatabase? = null

        fun getDatabase(context: Context): StudentsRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context): StudentsRoomDatabase {
            return Room.databaseBuilder(context, StudentsRoomDatabase::class.java, "student-db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}