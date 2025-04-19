package activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.library.databinding.LibraryItemBinding
import library.Library

class ItemAdapter(private val onItemClickListener: ((Library) -> Unit)): ListAdapter<Library, ItemViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LibraryItemBinding.inflate(LayoutInflater.from(parent.context))
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            onItemClickListener(getItem(position))
        }
    }
}