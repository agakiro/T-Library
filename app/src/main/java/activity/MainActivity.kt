package activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.library.databinding.ActivityMainBinding
import library.LibraryItemsCreator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val adapter = ItemAdapter(this)
    private val generator = LibraryItemsCreator()
    private val swipe = object : Swipe(){
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            adapter.deleteDataItem(libraryItems, viewHolder.adapterPosition)
        }
    }
    private val touchHelper = ItemTouchHelper(swipe)
    private val libraryItems = mutableListOf(
        generator.createBook(90743, true, "Маугли", 202, "Джозеф Киплинг"),
        generator.createNewspaper(17245, true, "Сельская жизнь", 794, "Апрель"),
        generator.createNewspaper(4, true, "Комсомольская правда", 52, "Март"),
        generator.createDisc(5, true, "Дэдпул и Росомаха", "DVD"),
        generator.createBook(7, true, "Алхимик", 223, "Пауло Коэльо"),
        generator.createDisc(111, true, "Bizkit", "CD")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(binding.root)
        init()
    }

    private fun init() {
        binding.apply {
            touchHelper.attachToRecyclerView(rcView)
            rcView.layoutManager = GridLayoutManager(this@MainActivity, 1)
            rcView.adapter = adapter
            adapter.setNewData(libraryItems)
        }
    }
}