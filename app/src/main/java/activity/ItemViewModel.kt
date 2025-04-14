package activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import library.Library
import library.LibraryItemsCreator

class ItemViewModel :ViewModel() {

    private val _libraryItems = MutableLiveData<List<Library>>()
    val libraryItems: LiveData<List<Library>> = _libraryItems

    private val _isAddingNewItem = MutableLiveData<Boolean>(false)
    val isAddingNewItem: LiveData<Boolean> = _isAddingNewItem

    private val _selectedItem = MutableLiveData<Library?>()
    val selectedItem: LiveData<Library?> = _selectedItem

    private val _addingItemType = MutableLiveData<String?>()
    val addingItemType: LiveData<String?> = _addingItemType

    private val _shouldCloseFragment = MutableLiveData<Boolean>(false)
    val shouldCloseFragment: LiveData<Boolean> = _shouldCloseFragment

    private val _scrollPosition = MutableLiveData<Int>(0)
    val scrollPosition: LiveData<Int> = _scrollPosition

    init {
        val generator = LibraryItemsCreator()
        _libraryItems.value = listOf(
            generator.createBook(90743, true, "Маугли", 202, "Джозеф Киплинг"),
            generator.createNewspaper(17245, true, "Сельская жизнь", 794, "Апрель"),
            generator.createNewspaper(4, true, "Комсомольская правда", 52, "Март"),
            generator.createDisc(5, true, "Дэдпул и Росомаха", "DVD"),
            generator.createBook(7, true, "Алхимик", 223, "Пауло Коэльо"),
            generator.createDisc(111, true, "Bizkit", "CD")
        )
    }

    fun addItem(newItem: Library) {
        val currentItems = _libraryItems.value?.toMutableList() ?: mutableListOf()
        currentItems.add(newItem)
        _libraryItems.value = currentItems
        _isAddingNewItem.value = false
        _scrollPosition.value = currentItems.size - 1
    }

    fun saveScrollPosition(position: Int) {
        _scrollPosition.value = position
    }

    fun closeFragment() {
        _shouldCloseFragment.value = true
    }

    fun selectItem(item: Library) {
        _selectedItem.value = item
        _isAddingNewItem.value = false
    }

    fun selectNewItemTypeBook() {
        _addingItemType.value = "Book"
    }

    fun selectNewItemTypeNewspaper() {
        _addingItemType.value = "Newspaper"
    }

    fun selectNewItemTypeDisc() {
        _addingItemType.value = "Disc"
    }

    fun startAddingNewItem() {
        _selectedItem.value = null
        _isAddingNewItem.value = true
    }

}