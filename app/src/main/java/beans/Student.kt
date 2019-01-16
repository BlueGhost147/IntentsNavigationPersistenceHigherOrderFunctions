package beans

import android.arch.persistence.room.*

@Entity(tableName = "students")
class Student(var name: String, @ColumnInfo(name = "current_semester", index = true) val currentSemester: Int) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    companion object {
        val intentId = "studentIdForPets"
    }
}
