package activity

import android.content.Context
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.library.databinding.LibraryItemBinding
import library.Library


class ItemViewHolder(private val binding: LibraryItemBinding, private val context: Context): RecyclerView.ViewHolder(binding.root) {

    fun bind(libraryItem: Library) = with(binding) {
        itemIcon.setImageResource(libraryItem.imageId)
        itemName.text = "Название: ${libraryItem.name}"
        itemName.alpha = changeAlpha(libraryItem)
        itemId.text = "id: ${libraryItem.id}"
        itemId.alpha = changeAlpha(libraryItem)
        itemCard.elevation = changeElevation(libraryItem)
        itemCard.setOnClickListener() {
            Toast.makeText(context, "Элемент с id ${libraryItem.id}", Toast.LENGTH_SHORT).show()
            libraryItem.isAvailable = !libraryItem.isAvailable
            itemCard.elevation = changeElevation(libraryItem)
            itemName.alpha = changeAlpha(libraryItem)
            itemId.alpha = changeAlpha(libraryItem)
        }
    }

    private fun changeElevation (libraryItem: Library): Float {
        return if (libraryItem.isAvailable) 10f else 1f
    }

    private fun changeAlpha (libraryItem: Library): Float {
        return if (libraryItem.isAvailable) 1f else 0.3f
    }
}

