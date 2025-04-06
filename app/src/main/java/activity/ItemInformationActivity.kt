package activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import com.example.library.R
import com.example.library.databinding.ActivityItemInformationBinding
import library.Book
import library.Disc
import library.Library
import library.Newspaper

class ItemInformationActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityItemInformationBinding.inflate(layoutInflater)
    }
    private lateinit var item: Library

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(binding.root)

        val isNew = intent.getBooleanExtra(ITEM_STATUS, true)
        if (intent.getStringExtra(ITEM_TYPE) == "Disc") binding.extraEditText2.isGone = true

        if (!isNew) {
            showItem()
        } else {
            createNewItem()
        }
    }

    private fun showItem() {
        item = when (intent.getStringExtra(ITEM_TYPE)) {
            "Book" -> Book(
                intent.getIntExtra(ITEM_PICTURE, 1),
                intent.getIntExtra(ITEM_ID, 0),
                intent.getBooleanExtra(IS_AVAILABLE, true),
                intent.getStringExtra(ITEM_NAME) ?: "",
                intent.getIntExtra(BOOK_PAGES, 0),
                intent.getStringExtra(BOOK_AUTHOR) ?: ""
            )

            "Newspaper" -> Newspaper(
                intent.getIntExtra(ITEM_PICTURE, 1),
                intent.getIntExtra(ITEM_ID, 0),
                intent.getBooleanExtra(IS_AVAILABLE, true),
                intent.getStringExtra(ITEM_NAME) ?: "",
                intent.getIntExtra(NEWSPAPER_RELEASE, 0),
                intent.getStringExtra(NEWSPAPER_MONTH) ?: ""
            )

            "Disc" -> Disc(
                intent.getIntExtra(ITEM_PICTURE, 1),
                intent.getIntExtra(ITEM_ID, 0),
                intent.getBooleanExtra(IS_AVAILABLE, true),
                intent.getStringExtra(ITEM_NAME) ?: "",
                intent.getStringExtra(DISC_TYPE) ?: ""
            )

            else -> throw IllegalArgumentException("Неизвестный тип данных")
        }
        setOldItems()
    }

    private fun createNewItem() {
        with(binding) {
            idEditText.hint = "Id"
            isAvailableTextText.hint = "Допступность: \"true/false\""
            nameEditText.hint = "Название"
            when (intent.getStringExtra(ITEM_TYPE)) {
                "Book" -> {
                    itemPic.setImageResource(R.drawable.book)
                    infoItemName.text = "Ваша книга"
                    extraEditText1.hint = "Количество страниц"
                    extraEditText2.hint = "Автор"
                }
                "Newspaper" -> {
                    itemPic.setImageResource(R.drawable.newspaper)
                    infoItemName.text = "Ваша газета"
                    extraEditText1.hint = "Номер выпуска"
                    extraEditText2.hint = "Месяц выпуска"
                }
                "Disc" -> {
                    itemPic.setImageResource(R.drawable.disc)
                    infoItemName.text = "Ваш диск"
                    extraEditText1.hint = "Тип диска"
                }
            }
            saveButton.setOnClickListener {
                item = when (intent.getStringExtra(ITEM_TYPE)) {
                    "Book" -> Book(
                        R.drawable.book,
                        idEditText.text.toString().toIntOrNull() ?: -1,
                        isAvailableTextText.text.toString().toBoolean(),
                        nameEditText.text.toString(),
                        extraEditText1.text.toString().toIntOrNull() ?: -1,
                        extraEditText2.text.toString()
                    )

                    "Newspaper" -> Newspaper(
                        R.drawable.newspaper,
                        idEditText.text.toString().toIntOrNull() ?: -1,
                        isAvailableTextText.text.toString().toBoolean(),
                        nameEditText.text.toString(),
                        extraEditText1.text.toString().toIntOrNull() ?: -1,
                        extraEditText2.text.toString()
                    )

                    "Disc" -> Disc(
                        R.drawable.disc,
                        idEditText.text.toString().toIntOrNull() ?: -1,
                        isAvailableTextText.text.toString().toBoolean(),
                        nameEditText.text.toString(),
                        extraEditText1.text.toString()
                    )
                    else -> throw IllegalArgumentException("Неизвестный тип данных")
                }
                setNewItem()
            }
        }
    }

    private fun setNewItem() {
        val resultIntent = when (item) {
            is Book -> Intent().apply {
                putExtra(ITEM_PICTURE, item.imageId)
                putExtra(ITEM_ID, item.id)
                putExtra(IS_AVAILABLE, item.isAvailable)
                putExtra(ITEM_NAME, item.name)
                putExtra(BOOK_AUTHOR, (item as Book).author)
                putExtra(BOOK_PAGES, (item as Book).pages)
                putExtra(ITEM_TYPE, "Book")
            }
            is Newspaper -> Intent().apply {
                putExtra(ITEM_PICTURE, item.imageId)
                putExtra(ITEM_ID, item.id)
                putExtra(IS_AVAILABLE, item.isAvailable)
                putExtra(ITEM_NAME, item.name)
                putExtra(NEWSPAPER_RELEASE, (item as Newspaper).releaseNumber)
                putExtra(NEWSPAPER_MONTH, (item as Newspaper).releaseMonth)
                putExtra(ITEM_TYPE, "Newspaper")
            }
            is Disc -> Intent().apply {
                putExtra(ITEM_PICTURE, item.imageId)
                putExtra(ITEM_ID, item.id)
                putExtra(IS_AVAILABLE, item.isAvailable)
                putExtra(ITEM_NAME, item.name)
                putExtra(DISC_TYPE, (item as Disc).type)
                putExtra(ITEM_TYPE, "Disc")
            }
            else -> throw IllegalArgumentException("Неизвестный тип данных")
        }

        setResult(RESULT_OK, resultIntent)
        finish()
    }



    @SuppressLint("SetTextI18n")
    private fun setOldItems() {
        with(binding) {
            infoItemName.text = item.name
            when (item) {
                is Book -> {
                    itemPic.setImageResource(item.imageId)
                    nameEditText.setText("Название: ${item.name}")
                    idEditText.setText("ID: ${item.id}")
                    isAvailableTextText.setText("Доступность: ${item.isAvailable}")
                    extraEditText1.setText("Кол-во страниц: ${(item as Book).pages}")
                    extraEditText2.setText("Автор: ${(item as Book).author}")
                }

                is Newspaper -> {
                    itemPic.setImageResource(item.imageId)
                    nameEditText.setText("Название: ${item.name}")
                    idEditText.setText("ID: ${item.id}")
                    isAvailableTextText.setText("Доступность: ${item.isAvailable}")
                    extraEditText1.setText("Номер выпуска: ${(item as Newspaper).releaseNumber}")
                    extraEditText2.setText("Месяц выпуска: ${(item as Newspaper).releaseMonth}")
                }

                is Disc -> {
                    itemPic.setImageResource(item.imageId)
                    nameEditText.setText("Название: ${item.name}")
                    idEditText.setText("ID: ${item.id}")
                    isAvailableTextText.setText("Доступность: ${item.isAvailable}")
                    extraEditText1.setText("Тип диска: ${(item as Disc).type}")
                    extraEditText2.isGone = true
                }
            }
            nameEditText.isFocusable = false
            nameEditText.isFocusable = false
            idEditText.isFocusable = false
            isAvailableTextText.isFocusable = false
            extraEditText1.isFocusable = false
            extraEditText2.isFocusable = false
            saveButton.isGone = true
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

        fun createIntent(context: Context): Intent {
            return Intent(context, ItemInformationActivity::class.java)
        }
    }
}
