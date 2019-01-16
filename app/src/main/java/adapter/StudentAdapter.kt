package adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import at.fh.swengb.intentspersistencehigherorder.R
import beans.Student
import kotlinx.android.synthetic.main.item_student.view.*

class StudentAdapter(val clickListener: (student: Student) -> Unit): RecyclerView.Adapter<StudentViewHolder>() {
    var studentList = listOf<Student>()

    fun updateList(newList: List<Student>) {
        studentList = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): StudentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val studentItemView = inflater.inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(studentItemView, clickListener)
    }

    override fun getItemCount(): Int {
        return studentList.size
    }

    override fun onBindViewHolder(viewHolder: StudentViewHolder, position: Int) {
        val student = studentList[position]
        viewHolder.bindItem(student) // Unresolved reference, i.e. the function bindItem does not exist. Why?
    }
}

class StudentViewHolder(myView: View,val clickListener: (student: Student) -> Unit): RecyclerView.ViewHolder(myView) {
    fun bindItem(student: Student) {
        itemView.edit_pet_name.text = "${student.id} ${student.name}"
        itemView.setOnClickListener {clickListener(student)}
    }
}