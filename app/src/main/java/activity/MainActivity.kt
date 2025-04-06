package activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuInflater
import android.widget.PopupMenu
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.library.databinding.ActivityMainBinding
import library.LibraryItemsCreator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.library.R
import library.Book
import library.Disc
import library.Newspaper

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val generator = LibraryItemsCreator()
    private val libraryItems = mutableListOf(
        generator.createBook(90743, true, "Маугли", 202, "Джозеф Киплинг"),
        generator.createNewspaper(17245, true, "Сельская жизнь", 794, "Апрель"),
        generator.createNewspaper(4, true, "Комсомольская правда", 52, "Март"),
        generator.createDisc(5, true, "Дэдпул и Росомаха", "DVD"),
        generator.createBook(7, true, "Алхимик", 223, "Пауло Коэльо"),
        generator.createDisc(111, true, "Bizkit", "CD")
    )
    private val adapter = ItemAdapter(libraryItems) { item ->
        val intent = Intent(this, ItemInformationActivity::class.java).apply{
            putExtra(ITEM_STATUS, false)
            putExtra(ITEM_PICTURE, item.imageId)
            putExtra(ITEM_ID, item.id)
            putExtra(IS_AVAILABLE, item.isAvailable)
            putExtra(ITEM_NAME, item.name)
            when (item){
                is Book -> {
                    putExtra(BOOK_AUTHOR, item.author)
                    putExtra(BOOK_PAGES, item.pages)
                    putExtra(ITEM_TYPE, "Book")
                }
                is Newspaper -> {
                    putExtra(NEWSPAPER_RELEASE, item.releaseNumber)
                    putExtra(NEWSPAPER_MONTH, item.releaseMonth)
                    putExtra(ITEM_TYPE, "Newspaper")
                }
                is Disc -> {
                    putExtra(DISC_TYPE, item.type)
                    putExtra(ITEM_TYPE, "Disc")
                }
            }
        }
        startActivity(intent)
    }
    private val swipe = object : Swipe(){
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            adapter.deleteDataItem(viewHolder.adapterPosition)
        }
    }
    private val touchHelper = ItemTouchHelper(swipe)

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            adapter.addDataItem(when (result.data?.getStringExtra(ITEM_TYPE)) {
                "Book" -> Book(
                    result.data?.getIntExtra(ITEM_PICTURE, 1) ?: 0,
                    result.data?.getIntExtra(ITEM_ID, 0) ?: 0,
                    result.data?.getBooleanExtra(IS_AVAILABLE, true) ?: false,
                    result.data?.getStringExtra(ITEM_NAME) ?: "",
                    result.data?.getIntExtra(BOOK_PAGES, 0) ?: 0,
                    result.data?.getStringExtra(BOOK_AUTHOR) ?: ""
                )

                "Newspaper" -> Newspaper(
                    result.data?.getIntExtra(ITEM_PICTURE, 1) ?: 0,
                    result.data?.getIntExtra(ITEM_ID, 0) ?: 0,
                    result.data?.getBooleanExtra(IS_AVAILABLE, true) ?: false,
                    result.data?.getStringExtra(ITEM_NAME) ?: "",
                    result.data?.getIntExtra(NEWSPAPER_RELEASE, 0) ?: 0,
                    result.data?.getStringExtra(NEWSPAPER_MONTH) ?: ""
                )

                "Disc" -> Disc(
                    result.data?.getIntExtra(ITEM_PICTURE, 1) ?: 0,
                    result.data?.getIntExtra(ITEM_ID, 0) ?: 0,
                    result.data?.getBooleanExtra(IS_AVAILABLE, true) ?: false,
                    result.data?.getStringExtra(ITEM_NAME) ?: "",
                    result.data?.getStringExtra(DISC_TYPE) ?: ""
                )

                else -> throw IllegalArgumentException("Неизвестный тип данных")
            })
        }
    }

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
            addButton.setOnClickListener {
                val popup = PopupMenu(this@MainActivity, it)
                val inflater: MenuInflater = popup.menuInflater
                inflater.inflate(R.menu.popup_menu_item, popup.menu)
                popup.setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.book -> {
                            startForResult.launch(ItemInformationActivity.createIntent(this@MainActivity).apply{
                                putExtra(ITEM_STATUS, true)
                                putExtra(ITEM_TYPE, "Book")
                            })
                            true
                        }
                        R.id.newspaper -> {
                            startForResult.launch(ItemInformationActivity.createIntent(this@MainActivity).apply{
                                putExtra(ITEM_STATUS, true)
                                putExtra(ITEM_TYPE, "Newspaper")
                            })
                            true
                        }
                        R.id.disc -> {
                            startForResult.launch(ItemInformationActivity.createIntent(this@MainActivity).apply{
                                putExtra(ITEM_STATUS, true)
                                putExtra(ITEM_TYPE, "Disc")
                            })
                            true
                        }
                        else -> false
                    }
                }
                popup.show()
            }
        }
    }
    companion object {
        const val ITEM_TYPE = "itemType"
        const val ITEM_PICTURE = "pic"
        const val ITEM_STATUS = "isNew"
        const val ITEM_ID = "id"
        const val IS_AVAILABLE = "isAvailable"
        const val ITEM_NAME = "name"
        const val DISC_TYPE = "type"
        const val NEWSPAPER_RELEASE = "releaseNumber"
        const val NEWSPAPER_MONTH = "releaseMonth"
        const val BOOK_PAGES = "pages"
        const val BOOK_AUTHOR = "author"
    }
}