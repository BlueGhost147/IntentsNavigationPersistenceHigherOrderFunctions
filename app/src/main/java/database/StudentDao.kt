package database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import beans.Student

@Dao
interface StudentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(student: Student): Long

    @Query("SELECT * from students")
    fun selectAllStudents(): List<Student>

    @Query("SELECT * from students where name LIKE '%' || :searchString || '%'")
    fun findByName(searchString: String): List<Student>
}