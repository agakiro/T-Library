package activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity.RESULT_CANCELED
import androidx.appcompat.app.AppCompatActivity.RESULT_OK
import androidx.core.view.isGone
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.example.library.R
import com.example.library.databinding.FragmentItemInformationBinding
import com.example.library.databinding.FragmentItemListBinding
import library.Book
import library.Disc
import library.Library
import library.Newspaper
import kotlin.properties.Delegates


class ItemInformationFragment : Fragment() {
    private val viewModel: ItemViewModel by activityViewModels()
    private lateinit var binding: FragmentItemInformationBinding
    private var isNew = true
    private var item: Library? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemInformationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            isNew = it.getBoolean(ARG_ITEM_IS_NEW)
            if(it.getString(ARG_ITEM_TYPE) == "Disc") binding.extraEditText2.isGone = true
            if (!isNew) {
                item = when (it.getString(ARG_ITEM_TYPE)) {
                    "Book" -> Book(
                        it.getInt(ARG_ITEM_PIC),
                        it.getInt(ARG_ITEM_ID),
                        it.getBoolean(ARG_ITEM_IS_AVAILABLE),
                        it.getString(ARG_ITEM_NAME)!!,
                        it.getInt(ARG_BOOK_PAGES),
                        it.getString(ARG_BOOK_AUTHOR)!!
                    )
                    "Newspaper" -> Newspaper(
                        it.getInt(ARG_ITEM_PIC),
                        it.getInt(ARG_ITEM_ID),
                        it.getBoolean(ARG_ITEM_IS_AVAILABLE),
                        it.getString(ARG_ITEM_NAME)!!,
                        it.getInt(ARG_NEWSPAPER_RELEASE),
                        it.getString(ARG_NEWSPAPER_MONTH)!!
                    )
                    "Disc" -> Disc(
                        it.getInt(ARG_ITEM_PIC),
                        it.getInt(ARG_ITEM_ID),
                        it.getBoolean(ARG_ITEM_IS_AVAILABLE),
                        it.getString(ARG_ITEM_NAME)!!,
                        it.getString(ARG_DISC_TYPE)!!
                    )
                    else -> throw IllegalArgumentException("Неизвестный тип данных")
                }
            }
        }

        if (!isNew) {
            showItem()
        } else {
            createNewItem()
        }
    }

    private fun createNewItem() {
        with(binding) {
            idEditText.hint = ID_HINT
            isAvailableTextText.hint = IS_AVAILABLE_HINT
            nameEditText.hint = NAME_HINT
            when (arguments?.getString(ARG_ITEM_TYPE)) {
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
                val resultItem = when (arguments?.getString(ARG_ITEM_TYPE)) {
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
                if (arguments?.getString(ARG_ITEM_TYPE) != "Disc") {
                    if (idEditText.text.isEmpty() || nameEditText.text.isEmpty() || isAvailableTextText.text.isEmpty() || extraEditText1.text.isEmpty() || extraEditText2.text.isEmpty()) {
                        viewModel.closeFragment()
                    } else {
                        viewModel.closeFragment()
                        viewModel.addItem(resultItem)
                    }
                } else {
                    if (idEditText.text.isEmpty() || nameEditText.text.isEmpty() || isAvailableTextText.text.isEmpty() || extraEditText1.text.isEmpty()) {
                        viewModel.closeFragment()
                    } else {
                        viewModel.closeFragment()
                        viewModel.addItem(resultItem)

                    }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showItem() {
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
            infoItemName.text = item?.name
            itemPic.setImageResource(item?.imageId ?: 0)
            nameEditText.setText("Название: ${item?.name}")
            idEditText.setText("ID: ${item?.id}")
            isAvailableTextText.setText("Доступность: ${item?.isAvailable}")
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
        private const val ARG_ITEM_TYPE = "itemType"
        private const val ARG_ITEM_IS_NEW = "isNew"
        private const val ARG_ITEM_PIC = "itemPicture"
        private const val ARG_ITEM_ID = "itemId"
        private const val ARG_ITEM_NAME = "itemName"
        private const val ARG_ITEM_IS_AVAILABLE = "itemIsAvailable"
        private const val ARG_BOOK_PAGES = "bookPages"
        private const val ARG_BOOK_AUTHOR = "bookAuthor"
        private const val ARG_NEWSPAPER_RELEASE = "newspaperRelease"
        private const val ARG_NEWSPAPER_MONTH = "newspaperMonth"
        private const val ARG_DISC_TYPE = "discType"
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

        fun newInstance(item: Library?, isNew: Boolean, itemType: String?) =
            ItemInformationFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(ARG_ITEM_IS_NEW, isNew)
                    if (!isNew) {
                        putInt(ARG_ITEM_PIC, item?.imageId ?: 0)
                        putInt(ARG_ITEM_ID, item?.id ?: 0)
                        putString(ARG_ITEM_NAME, item?.name ?: "")
                        putBoolean(ARG_ITEM_IS_AVAILABLE, item?.isAvailable ?: true)
                        when (item) {
                            is Book -> {
                                putString(ARG_ITEM_TYPE, "Book")
                                putInt(ARG_BOOK_PAGES, item.pages)
                                putString(ARG_BOOK_AUTHOR, item.author)
                            }

                            is Newspaper -> {
                                putString(ARG_ITEM_TYPE, "Newspaper")
                                putInt(ARG_NEWSPAPER_RELEASE, item.releaseNumber)
                                putString(ARG_NEWSPAPER_MONTH, item.releaseMonth)
                            }

                            is Disc -> {
                                putString(ARG_ITEM_TYPE, "Disc")
                                putString(ARG_DISC_TYPE, item.type)
                            }
                        }
                    } else {
                        putString(ARG_ITEM_TYPE, itemType)
                    }
                }
            }
    }
}