package adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import at.fh.swengb.intentspersistencehigherorder.R
import beans.Pet
import kotlinx.android.synthetic.main.item_pet.view.*

class PetAdapter(val onclick : ((pet : Pet) -> Unit),val longclick : ((pet : Pet) -> Boolean)): RecyclerView.Adapter<PetViewHolder>() {
    var petList = listOf<Pet>()

    fun updateList(newList: List<Pet>) {
        petList = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): PetViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val petItemView = inflater.inflate(R.layout.item_pet, parent, false)
        return PetViewHolder(petItemView,onclick, longclick)
    }

    override fun getItemCount(): Int {
        return petList.size
    }

    override fun onBindViewHolder(viewHolder: PetViewHolder, position: Int) {
        val pet = petList[position]
        viewHolder.bindItem(pet)
    }
}

class PetViewHolder(myView: View, val onclick : ((pet : Pet) -> Unit), val longclick : ((pet : Pet) -> Boolean)): RecyclerView.ViewHolder(myView) {
    fun bindItem(pet: Pet) {
        itemView.edit_pet_name.text = pet.name
        itemView.setOnClickListener { onclick(pet) }
        itemView.setOnLongClickListener { longclick(pet) }
    }
}