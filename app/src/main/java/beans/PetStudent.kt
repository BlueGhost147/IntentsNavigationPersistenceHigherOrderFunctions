package beans

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation

class PetStudent {
    @Embedded
    lateinit var student: Student
    @Relation(entity = Pet::class, entityColumn = "studentId", parentColumn = "id")
    lateinit var pets : List<Pet>
}