package activity

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.library.databinding.LibraryItemBinding
import library.Library

class ItemAdapter(private val context: Context): RecyclerView.Adapter<ItemViewHolder>() {

    private val data = mutableListOf<Library>()

    fun setNewData(newData: MutableList<Library>) {
        data.addAll(newData)
        notifyDataSetChanged()
    }

    fun deleteDataItem(currentData: MutableList<Library>, position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LibraryItemBinding.inflate(LayoutInflater.from(parent.context))
        return ItemViewHolder(view, context)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(data[position])
    }
}