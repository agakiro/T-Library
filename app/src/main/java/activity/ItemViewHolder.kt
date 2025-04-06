package activity

import androidx.recyclerview.widget.RecyclerView
import com.example.library.databinding.LibraryItemBinding
import library.Library


class ItemViewHolder(private val binding: LibraryItemBinding): RecyclerView.ViewHolder(binding.root) {


    fun bind(libraryItem: Library) = with(binding) {
        itemIcon.setImageResource(libraryItem.imageId)
        itemName.text = "Название: ${libraryItem.name}"
        itemName.alpha = changeAlpha(libraryItem)
        itemId.text = "id: ${libraryItem.id}"
        itemId.alpha = changeAlpha(libraryItem)
        itemCard.elevation = changeElevation(libraryItem)
    }

    private fun changeElevation (libraryItem: Library): Float {
        return if (libraryItem.isAvailable) 10f else 1f
    }

    private fun changeAlpha (libraryItem: Library): Float {
        return if (libraryItem.isAvailable) 1f else 0.3f
    }
}

