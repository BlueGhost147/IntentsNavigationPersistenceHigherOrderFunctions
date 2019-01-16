package at.fh.swengb.intentspersistencehigherorder

import adapter.PetAdapter
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import beans.Pet
import beans.Student
import database.StudentsRoomDatabase
import kotlinx.android.synthetic.main.activity_pet_list.*
import java.net.URI


class PetListActivity : AppCompatActivity() {

    private lateinit var petAdapter: PetAdapter
    private lateinit var db: StudentsRoomDatabase
    private lateinit var student: Student

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_list)
        petAdapter = PetAdapter {
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_TEXT, it.name)
            intent.type = "text/plain"
            val chooserIntent = Intent.createChooser(intent, "Share my pet")
            startActivity(chooserIntent)
            true
        }
        db = StudentsRoomDatabase.getDatabase(applicationContext)
        recycler_view.adapter = petAdapter
        recycler_view.layoutManager = LinearLayoutManager(this)

        //val list = listOf<Pet>(Pet("Hello", 2), Pet("Hello2", 4))
        // petAdapter.updateList(lsit)
        val petStudent = db.petDao.findStudentsAndPets(
            intent.getLongExtra(Student.intentId,0)
        )
        petAdapter.updateList(petStudent.pets)
        student = petStudent.student

        textView_studentName.text = "Pets of ${student.name}"
    }

    fun addPetForStudent(v: View) {
        db.petDao.insert(Pet(edit_pet_name.text.toString(),student.id))
        petAdapter.updateList(db.petDao.findStudentsAndPets(student.id).pets)
    }

}


