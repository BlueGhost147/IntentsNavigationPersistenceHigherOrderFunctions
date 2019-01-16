package database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import beans.Pet
import beans.PetStudent
import beans.Student

@Dao
interface PetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pet: Pet): Long

    @Query("SELECT * from pets")
    fun selectAllPets(): List<Pet>

    @Query("SELECT * from pets where name LIKE '%' || :searchString || '%'")
    fun findByName(searchString: String): List<Pet>

    @Query("SELECT * from students")
    fun findAllStudentsAndPets(): List<PetStudent>

    @Query("SELECT * from students where id = :id")
    fun findStudentsAndPets(id : Long): PetStudent
}