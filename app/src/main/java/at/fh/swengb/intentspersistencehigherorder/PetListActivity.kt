package at.fh.swengb.intentspersistencehigherorder

import adapter.PetAdapter
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import beans.Pet
import beans.Student
import database.StudentsRoomDatabase
import kotlinx.android.synthetic.main.activity_pet_list.*
import java.net.URI


class PetListActivity : AppCompatActivity() {

    private lateinit var petAdapter: PetAdapter
    private lateinit var db: StudentsRoomDatabase
    private lateinit var student: Student

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menue, menu)
        return true
    }

    inline fun consume(f: () -> Unit): Boolean {
        f()
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            R.id.logout -> consume { Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show() }
            R.id.share -> consume { Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show() }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_list)
        petAdapter = PetAdapter(
            {
                val intent = Intent(Intent.ACTION_SEND)
                intent.putExtra(Intent.EXTRA_TEXT, it.name)
                intent.type = "text/plain"
                val chooserIntent = Intent.createChooser(intent, getString(R.string.action_share_pet))
                startActivity(chooserIntent)
            },
            {
                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setTitle(getString(R.string.action_delete_pet))
                dialogBuilder.setMessage("Are you sure you want to delete the Pet ${it.name}?")
                dialogBuilder.setPositiveButton(getString(R.string.action_yes)) { _, _ ->
                    db.petDao.delete(it)
                    petAdapter.updateList(db.petDao.findStudentsAndPets(student.id).pets)
                }
                dialogBuilder.setNegativeButton(getString(R.string.action_no), null)
                dialogBuilder.show()
                true
            }

        )
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
        val newPet = Pet(
            edit_pet_name.text.toString(),
            student.id
        )

        db.petDao.insert(
            newPet
        )
        petAdapter.updateList(db.petDao.findStudentsAndPets(student.id).pets)
    }

}


