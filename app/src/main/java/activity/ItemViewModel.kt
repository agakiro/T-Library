package activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import library.Library
import library.LibraryItemsCreator
import kotlin.random.Random

class ItemViewModel :ViewModel() {

    private val _libraryItems = MutableStateFlow<List<Library>>(emptyList())
    val libraryItems: StateFlow<List<Library>> = _libraryItems

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isUpdating = MutableStateFlow(true)
    val isUpdating: StateFlow<Boolean> = _isUpdating

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

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

    private val _informationFragmentVisibility = MutableLiveData<Boolean>()
    val informationFragmentVisibility: LiveData<Boolean> = _informationFragmentVisibility


    init {
        loadItems()
    }

    fun loadItems() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            runCatching {
                delay(Random.nextLong(100, 2000))
                if (Random.nextInt(4) == 0) throw Exception(LOADING_ERROR)
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
                .onFailure { e -> _error.value = e.message ?: UNKNOWN_ERROR }

            _isLoading.value = false
        }
    }

    fun addItem(newItem: Library) {
        val currentItems = _libraryItems.value.toMutableList()
        viewModelScope.launch {
            _isUpdating.value = true
            _error.value = null

            runCatching {
                delay(Random.nextLong(100, 1000))
                if (Random.nextInt(4) == 0) throw Exception(SAVING_ERROR)
                currentItems.add(newItem)
            }
                .onSuccess { _libraryItems.value = currentItems }
                .onFailure { e ->  _error.value = e.message ?: UNKNOWN_ERROR }
            _isUpdating.value = false
            _isAddingNewItem.value = false
        }
        _scrollPosition.value = currentItems.size - 1
        _isAddingNewItem.value = false
    }

    fun makeInformationInvisible() {
        _informationFragmentVisibility.value = false
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
        _informationFragmentVisibility.value = true
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
        _informationFragmentVisibility.value = true
    }

    companion object {
        private const val LOADING_ERROR = "Ошибка загрузки данных"
        private const val SAVING_ERROR = "Ошибка сохранения данных"
        private const val UNKNOWN_ERROR = "Неизвестная ошибка"
    }

}