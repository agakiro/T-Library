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
                intent.getIntExtra(ITEM_PICTURE, GET_PIC_DEFAULT_VALUE),
                intent.getIntExtra(ITEM_ID, GET_EXTRA_DEFAULT_VALUE),
                intent.getBooleanExtra(IS_AVAILABLE, true),
                intent.getStringExtra(ITEM_NAME) ?: "",
                intent.getIntExtra(BOOK_PAGES, GET_EXTRA_DEFAULT_VALUE),
                intent.getStringExtra(BOOK_AUTHOR) ?: ""
            )

            "Newspaper" -> Newspaper(
                intent.getIntExtra(ITEM_PICTURE, GET_PIC_DEFAULT_VALUE),
                intent.getIntExtra(ITEM_ID, GET_EXTRA_DEFAULT_VALUE),
                intent.getBooleanExtra(IS_AVAILABLE, true),
                intent.getStringExtra(ITEM_NAME) ?: "",
                intent.getIntExtra(NEWSPAPER_RELEASE, GET_EXTRA_DEFAULT_VALUE),
                intent.getStringExtra(NEWSPAPER_MONTH) ?: ""
            )

            "Disc" -> Disc(
                intent.getIntExtra(ITEM_PICTURE, GET_PIC_DEFAULT_VALUE),
                intent.getIntExtra(ITEM_ID, GET_EXTRA_DEFAULT_VALUE),
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
            idEditText.hint = ID_HINT
            isAvailableTextText.hint = IS_AVAILABLE_HINT
            nameEditText.hint = NAME_HINT
            when (intent.getStringExtra(ITEM_TYPE)) {
                "Book" -> {
                    itemPic.setImageResource(R.drawable.book)
                    infoItemName.text = BOOK_INFO_NAME
                    extraEditText1.hint = PAGES_HINT
                    extraEditText2.hint = AUTHOR_HINT
                }
                "Newspaper" -> {
                    itemPic.setImageResource(R.drawable.newspaper)
                    infoItemName.text = NEWSPAPER_INFO_NAME
                    extraEditText1.hint = RELEASE_HINT
                    extraEditText2.hint = MONTH_HINT
                }
                "Disc" -> {
                    itemPic.setImageResource(R.drawable.disc)
                    infoItemName.text = DISC_INFO_NAME
                    extraEditText1.hint = DISC_TYPE_HINT
                }
            }
            saveButton.setOnClickListener {
                item = when (intent.getStringExtra(ITEM_TYPE)) {
                    "Book" -> Book(
                        R.drawable.book,
                        idEditText.text.toString().toIntOrNull() ?: EMPTY_ED_DEFAULT_VALUE,
                        isAvailableTextText.text.toString().toBoolean(),
                        nameEditText.text.toString(),
                        extraEditText1.text.toString().toIntOrNull() ?: EMPTY_ED_DEFAULT_VALUE,
                        extraEditText2.text.toString()
                    )

                    "Newspaper" -> Newspaper(
                        R.drawable.newspaper,
                        idEditText.text.toString().toIntOrNull() ?: EMPTY_ED_DEFAULT_VALUE,
                        isAvailableTextText.text.toString().toBoolean(),
                        nameEditText.text.toString(),
                        extraEditText1.text.toString().toIntOrNull() ?: EMPTY_ED_DEFAULT_VALUE,
                        extraEditText2.text.toString()
                    )

                    "Disc" -> Disc(
                        R.drawable.disc,
                        idEditText.text.toString().toIntOrNull() ?: EMPTY_ED_DEFAULT_VALUE,
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
        val resultIntent = setResultIntent(intent.getStringExtra(ITEM_TYPE), item)
        resultSetting(resultIntent)
        finish()
    }

    private fun setResultIntent(type: String?, item: Library): Intent {
        val result = Intent().apply {
            putExtra(ITEM_PICTURE, item.imageId)
            putExtra(ITEM_ID, item.id)
            putExtra(IS_AVAILABLE, item.isAvailable)
            putExtra(ITEM_NAME, item.name)
            putExtra(ITEM_TYPE, type)
        }
        when (type) {
            "Book" -> result.apply {
                putExtra(BOOK_AUTHOR, (item as Book).author)
                putExtra(BOOK_PAGES, item.pages)
            }
            "Newspaper" -> result.apply {
                putExtra(NEWSPAPER_RELEASE, (item as Newspaper).releaseNumber)
                putExtra(NEWSPAPER_MONTH, item.releaseMonth)
            }
            "Disc" -> result.apply {
                putExtra(DISC_TYPE, (item as Disc).type)
            }
        }
        return result
    }

    private fun resultSetting (resultIntent: Intent) {
        with (binding) {
            if (intent.getStringExtra(ITEM_TYPE) != "Disc") {
                if (idEditText.text.isEmpty() || nameEditText.text.isEmpty() || isAvailableTextText.text.isEmpty() || extraEditText1.text.isEmpty() || extraEditText2.text.isEmpty()) {
                    setResult(RESULT_CANCELED, resultIntent)
                } else {
                    setResult(RESULT_OK, resultIntent)
                }
            } else {
                if (idEditText.text.isEmpty() || nameEditText.text.isEmpty() || isAvailableTextText.text.isEmpty() || extraEditText1.text.isEmpty()) {
                    setResult(RESULT_CANCELED, resultIntent)
                } else {
                    setResult(RESULT_OK, resultIntent)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setOldItems() {
        with(binding) {
            setGeneralDescription()
            when (item) {
                is Book -> {
                    extraEditText1.setText("Кол-во страниц: ${(item as Book).pages}")
                    extraEditText2.setText("Автор: ${(item as Book).author}")
                }

                is Newspaper -> {
                    extraEditText1.setText("Номер выпуска: ${(item as Newspaper).releaseNumber}")
                    extraEditText2.setText("Месяц выпуска: ${(item as Newspaper).releaseMonth}")
                }

                is Disc -> {
                    extraEditText1.setText("Тип диска: ${(item as Disc).type}")
                    extraEditText2.isGone = true
                }
            }
            setUnavailable()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setGeneralDescription() {
        with(binding) {
            infoItemName.text = item.name
            itemPic.setImageResource(item.imageId)
            nameEditText.setText("Название: ${item.name}")
            idEditText.setText("ID: ${item.id}")
            isAvailableTextText.setText("Доступность: ${item.isAvailable}")
        }
    }

    private fun setUnavailable () {
        with(binding) {
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
        const val ID_HINT = "Id"
        const val IS_AVAILABLE_HINT = "Доступность: \"true/false\""
        const val NAME_HINT = "Название"
        const val BOOK_INFO_NAME = "Ваша книга"
        const val PAGES_HINT = "Кол-во страниц"
        const val AUTHOR_HINT = "Автор"
        const val NEWSPAPER_INFO_NAME = "Ваша газета"
        const val RELEASE_HINT = "Номер выпуска"
        const val MONTH_HINT = "Месяц выпуска"
        const val DISC_INFO_NAME = "Ваш диск"
        const val DISC_TYPE_HINT = "Тип диска: CD/DVD"
        const val EMPTY_ED_DEFAULT_VALUE = -1
        const val GET_PIC_DEFAULT_VALUE = 1
        const val GET_EXTRA_DEFAULT_VALUE = 0

        fun createIntent(context: Context): Intent {
            return Intent(context, ItemInformationActivity::class.java)
        }
    }
}
