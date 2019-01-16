package at.fh.swengb.intentspersistencehigherorder

import adapter.StudentAdapter
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import beans.Student
import database.StudentsRoomDatabase
import kotlinx.android.synthetic.main.activity_student_list.*

class StudentListActivity : AppCompatActivity() {

    private lateinit var studentAdapter: StudentAdapter
    private lateinit var db: StudentsRoomDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_list)

        studentAdapter = StudentAdapter { student ->
            val intent = Intent(applicationContext, PetListActivity::class.java)
            intent.putExtra(Student.intentId,student.id)
            startActivity(intent)

            //Toast.makeText(this, "Student clicked: ${student.name}", Toast.LENGTH_SHORT).show()
        }
        recycler_view.adapter = studentAdapter
        recycler_view.layoutManager = LinearLayoutManager(this)
        db = StudentsRoomDatabase.getDatabase(this)

        myHigherOrderFunction {
            it * 102
        }

        kotlinHigherOrderWithList()
    }

    override fun onResume() {
        super.onResume()
        studentAdapter.updateList(getAllStudentsFromDatabase())
    }

    fun addStudent(view: View) {
        val name = edit_pet_name.text.toString()
        val currentSemester = current_semester.text.toString().toIntOrNull() ?: 0

        val student = Student(name = name, currentSemester = currentSemester)
        db.studentDao.insert(student)
        studentAdapter.updateList(getAllStudentsFromDatabase())
    }

    fun getAllStudentsFromDatabase(): List<Student> {
        return db.studentDao.selectAllStudents()
    }

    private fun myHigherOrderFunction(param: (Int) -> Int) {
        if (param(6) == 612) {
            Log.e("HIGHER_ORDER", "Congrats, your first lambda works :)")
        }
    }

    private fun kotlinHigherOrderWithList() {
        val list = listOf(Student("Jon", 1), Student("Arya", 1), Student("Eddard", 3), Student("Cersei", 4), Student("Robert", 5))

        list.filter { it.currentSemester >= 2 }
            .sortedBy { it.name }
            .map { it.name.toUpperCase() }
            .forEach { Log.i("HIGHER_ORDER",it) }

        // Use Kotlins built-in higher order functions to filter out all students below the 2nd Semester
        //Order them by their name
        //make their names uppercase
        //log out each of the resulting strings

    }
}
