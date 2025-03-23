
fun main(){

    val book1: Book = Book(90743, true, "Маугли", 202, "Джозеф Киплинг")
    val book2: Book = Book(7, true, "Алхимик", 223, "Пауло Коэльо")
    val newspaper1: Newspaper = Newspaper(17245, true, "Сельская жизнь", 794, "Апрель")
    val newspaper2: Newspaper = Newspaper(4, true, "Комсомольская правда", 52, "Март")
    val disc1: Disc = Disc(5, true, "Дэдпул и Росомаха", "DVD")
    val disc2: Disc = Disc(111, true, "Bizkit", "CD")
    val action = Action()
    val digitizer1 = Digitizer()
    val digitizedBook1: Disc = digitizer1.digitize(book2)
    val libraryItems = mutableListOf(book1, book2, newspaper1, newspaper2, disc1, disc2, digitizedBook1)
    val bookList = toType<Library, Book>(libraryItems)

    while (true) {
        println("""
        Выберите действие:
        1 - Показать книги
        2 - Показать газеты
        3 - Показать диски
        4 - Управление менеджером
        0 - Выйти
        """.trimIndent())
        when (readln()) {
            "1" -> action.showBooks(libraryItems)
            "2" -> action.showNewspapers(libraryItems)
            "3" -> action.showDiscs(libraryItems)
            "4" -> action.manageManager()
            "0" -> break
            else -> println("Ошибка ввода, повторите попытку")
        }
    }
}

abstract class Library(val id: Int, var isAvailable: Boolean, val name: String): Returnable, Readable, Takeable {
    abstract  fun getDetailedInformation()

    fun getSummaryInformation(): String {
        return "$name доступна: ${if (isAvailable) "Да" else "Нет" }"
    }
}

class Book(id: Int, isAvailable: Boolean, name: String, private val pages: Int, private val author: String): Library(id, isAvailable, name) {
    override fun getDetailedInformation() {
        println("книга: $name ($pages стр.) автора: $author с id: $id доступна: ${if (isAvailable) "Да" else "Нет" }")
    }

    override fun takeBack() {
        if (!isAvailable) {
            println("Книгу $id вернули в библиотеку")
            isAvailable = true
        } else {
            println("Действие невозможно, объект присутствует в библиотеке")
        }
    }

    override fun read() {
        if (isAvailable){
            println("Книгу $id взяли в читальный зал")
            isAvailable = false
        } else {
            println("Действие невозможно, объект недоступен")
        }
    }

    override fun take() {
        if (isAvailable){
            println("Книгу $id взяли домой")
            isAvailable = false
        } else {
            println("Действие невозможно, объект недоступен")
        }
    }
}

class Newspaper(id: Int, isAvailable: Boolean, name: String, private val releaseNumber: Int, private val releaseMonth: String) : Library(id, isAvailable, name), Readable {
    override fun getDetailedInformation() {
        println("выпуск: $releaseNumber газеты $name, выпущенной в месяце: $releaseMonth с id: $id доступен: ${if (isAvailable) "Да" else "Нет" }")
    }

    override fun takeBack() {
        if (!isAvailable) {
            println("Газету $id вернули в библиотеку")
            isAvailable = true
        } else {
            println("Действие невозможно, объект присутствует в библиотеке")
        }
    }

    override fun read() {
        if (isAvailable){
            println("Газету $id взяли в читальный зал")
            isAvailable = false
        } else {
            println("Действие невозможно, объект недоступен")
        }
    }

    override fun take() {
        println("Действие невозможно, газету нельзя взять с собой")
    }
}

class Disc(id: Int, isAvailable: Boolean, name: String, private val type: String) : Library(id, isAvailable, name), Takeable {
    override fun getDetailedInformation() {
        println("$type $name достуен: ${if (isAvailable) "Да" else "Нет" }")
    }

    override fun takeBack() {
        if (!isAvailable) {
            println("Диск $id вернули в библиотеку")
            isAvailable = true
        } else {
            println("Действие невозможно, объект присутствует в библиотеке")
        }
    }

    override fun read() {
        println("Действие невозможно, диск нельзя читать в зале")
    }

    override fun take() {
        if (isAvailable){
            println("Диск $id взяли домой")
            isAvailable = false
        } else {
            println("Действие невозможно, объект недоступен")
        }
    }
}
