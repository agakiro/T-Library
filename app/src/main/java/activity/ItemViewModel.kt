package activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import db.ItemRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import library.Library
import library.LibraryItemsCreator
import kotlin.random.Random

class ItemViewModel(private val repository: ItemRepository) :ViewModel() {

    private val _libraryItems = MutableStateFlow<List<Library>>(emptyList())
    val libraryItems: StateFlow<List<Library>> = _libraryItems

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isUpdating = MutableStateFlow(false)
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

    private val generator = LibraryItemsCreator()

    init {
        viewModelScope.launch {
            initializeItems()
        }
    }

    suspend fun initializeItems() {
        _isLoading.value = true
        runCatching {
            val items = repository.loadAllItems()
            if (items.isEmpty()) {
                listOf(
                    generator.createBook(1, true, "Маугли", 202, "Джозеф Киплинг"),
                    generator.createNewspaper(2, true, "Сельская жизнь", 794, "Апрель"),
                    generator.createNewspaper(3, true, "Комсомольская правда", 52, "Март"),
                    generator.createDisc(4, true, "Дэдпул и Росомаха", "DVD"),
                    generator.createBook(5, true, "Алхимик", 223, "Пауло Коэльо"),
                    generator.createDisc(6, true, "Bizkit", "CD"),
                    generator.createBook(7, true, "oaoaoaoa", 444, "ыыыы"),
                    generator.createNewspaper(8, true, "iiiiiiiiiii", 794, "Апрель"),
                    generator.createNewspaper(9, true, "GAZETA", 52, "Март"),
                    generator.createDisc(10, true, "pomogite", "DVD"),
                    generator.createBook(11, true, "anotherBook", 13323, "ya"),
                    generator.createDisc(12, true, "Dota 2", "CD"),
                    generator.createBook(13, true, "mowgli", 333, "AAAAAA"),
                    generator.createNewspaper(14, true, "Breakpoint", 794, "November"),
                    generator.createNewspaper(15, true, "Pravda", 0, "Март"),
                    generator.createDisc(16, true, "Diaaaaaaaaa", "DVD"),
                    generator.createBook(17, true, "qqqqqqq", 223, "Пауло Коэльо"),
                    generator.createDisc(18, true, "WW", "CD"),
                    generator.createBook(19, true, "Маугли", 202, "Джозеф Киплинг"),
                    generator.createNewspaper(20, true, "Сельская жизнь", 794, "Апрель"),
                    generator.createNewspaper(21, true, "Pravda", 52, "November"),
                    generator.createDisc(22, true, "Дэдпул и Росомаха", "DVD"),
                    generator.createBook(23, true, "Алхимик", 223, "Пауло Коэльо"),
                    generator.createDisc(24, true, "Last", "CD")
                ).forEach {
                    repository.insertItem(it)
                }
            }
            loadItems()
        }
            .onFailure { e -> _error.value = e.message ?: UNKNOWN_ERROR }
        _isLoading.value = false
    }

     private fun loadItems() {
        viewModelScope.launch {
            runCatching {
                delay(Random.nextLong(100, 2000))
                if (Random.nextInt(4) == 0) throw Exception(LOADING_ERROR)
                _libraryItems.value = repository.loadInitialPage()
            }
                .onFailure { e -> _error.value = e.message ?: UNKNOWN_ERROR }
        }
    }

    fun loadNextPage() {
        viewModelScope.launch {
            _isUpdating.value = true
            runCatching {
                delay(1500)
                val newItems = repository.loadNextPage()
                _libraryItems.value = (_libraryItems.value + newItems).takeLast(_libraryItems.value.size + newItems.size)
            }
                .onFailure { e -> _error.value = e.message ?: UNKNOWN_ERROR }
            _isUpdating.value = false
        }
    }

    fun loadPreviousPage() {
        viewModelScope.launch {
            _isUpdating.value = true
            runCatching {
                delay(1500)
                val newItems = repository.loadPreviousPage()
                _libraryItems.value = (newItems + _libraryItems.value).take(_libraryItems.value.size + newItems.size)
            }
                .onFailure { e -> _error.value = e.message ?: UNKNOWN_ERROR }
                _isUpdating.value = false
        }
    }

    fun addItem(newItem: Library) {
        viewModelScope.launch {
            _isUpdating.value = true
            _error.value = null

            runCatching {
                delay(Random.nextLong(100, 1000))
                if (Random.nextInt(4) == 0) throw Exception(SAVING_ERROR)
                repository.insertItem(newItem)
                _libraryItems.value = repository.loadAllItems()
                _scrollPosition.value = _libraryItems.value.size - 1
            }
                .onFailure { e ->  _error.value = e.message ?: UNKNOWN_ERROR }
            _isUpdating.value = false
            _isAddingNewItem.value = false
        }
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

class ItemViewModelFactory(private val repository: ItemRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItemViewModel::class.java)) {
            return ItemViewModel(repository) as T
        }
        throw IllegalArgumentException("Неизвестный ViewModel class")
    }
}