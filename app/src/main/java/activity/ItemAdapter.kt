package activity

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.library.databinding.LibraryItemBinding
import library.Library

class ItemAdapter(private val data: MutableList<Library>, private val onItemClickListener: ((Library) -> Unit)): RecyclerView.Adapter<ItemViewHolder>() {

    fun setNewData(newData: MutableList<Library>) {
        data.addAll(newData)
        notifyDataSetChanged()
    }

    fun addDataItem(item: Library) {
        data.add(item)
        notifyDataSetChanged()
    }

    fun deleteDataItem(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LibraryItemBinding.inflate(LayoutInflater.from(parent.context))
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(data[position])
        holder.itemView.setOnClickListener {
            onItemClickListener(data[position])
        }
    }
}