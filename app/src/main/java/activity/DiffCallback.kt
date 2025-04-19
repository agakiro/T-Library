package activity

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import library.Library

class DiffCallback : DiffUtil.ItemCallback<Library>() {
    override fun areItemsTheSame(oldItem: Library, newItem: Library): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Library, newItem: Library): Boolean {
        return oldItem == newItem
    }
}