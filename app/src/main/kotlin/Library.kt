


fun main(){
    val book1: Library = Book(90743, true, "Маугли", 202, "Джозеф Киплинг")
    val book2: Library = Book(7, true, "Алхимик", 223, "Пауло Коэльо")
    val newspaper1: Library = Newspaper(17245, true, "Сельская жизнь", 794)
    val newspaper2: Library = Newspaper(4, true, "Комсомольская правда", 52)
    val disc1: Library = Disc(5, true, "Дэдпул и Росомаха", "DVD")
    val disc2: Library = Disc(111, true, "Bizkit", "CD")


    val action = Action()
    val libraryItems = listOf(book1, book2, newspaper1, newspaper2, disc1, disc2)


    while (true) {
        println("""
        Выберите действие:
        1 - Показать книги
        2 - Показать газеты
        3 - Показать диски
        0 - Выйти
    """.trimIndent())
        when (readln()) {
            "1" -> action.showBooks(libraryItems)
            "2" -> action.showNewspapers(libraryItems)
            "3" -> action.showDiscs(libraryItems)
            "0" -> break
            else -> println("Ошибка ввода, повторите попытку")
        }
    }

}


class Action {

    fun showBooks(arr: List<Library>) {
        do {
            println("*Отображён список книг*")
            println("Выберите номер объекта")
            val books = mutableListOf<Book>()
            for (i in arr) {
                if (i is Book) {
                    books.add(i)
                    println("${books.indexOf(i) + 1} - ${i.getSummaryInformation()}")
                }
            }
            println("0 - Вернуться в меню")

            val indexOfItem = readln()
            if (!doWith(books, indexOfItem)) {
                return
            }
        } while (indexOfItem != "0")
    }


    fun showNewspapers(arr: List<Library>) {
        do {
            println("*Отображён список газет*")
            println("Выберите номер объекта")
            val newspapers = mutableListOf<Newspaper>()
            for (i in arr) {
                if (i is Newspaper) {
                    newspapers.add(i)
                    println("${newspapers.indexOf(i) + 1} - ${i.getSummaryInformation()}")

                }
            }
            println("0 - Вернуться в меню")

            val indexOfItem = readln()
            if (!doWith(newspapers, indexOfItem)) {
                return
            }
        } while (indexOfItem != "0")
    }

    fun showDiscs (arr: List<Library>) {

        do {
            println("*Отображён список дисков*")
            println("Выберите номер объекта:")
            val discs = mutableListOf<Disc>()
            for (i in arr) {
                if (i is Disc) {
                    discs.add(i)
                    println("${discs.indexOf(i) + 1} - ${i.getSummaryInformation()}")
                }
            }
            println("0 - Вернуться в меню")

            val indexOfItem = readln()
            if (!doWith(discs, indexOfItem)) {
                return
            }
        } while (indexOfItem != "0")
    }

    private fun doWith(itemsList: List<Library>, indexOfItem: String): Boolean {
        if (indexOfItem == "0") {
            return true
        }

        try {
            indexOfItem.toInt()
            while (true) {
                println("Выберите действие:")
                println("""
                1 - Взять домой
                2 - Читать в читальном зале
                3 - Показать подробную информацию
                4 - Вернуть
                0 - Вернуться в меню
            """.trimIndent()
                )

                when (readln()) {
                    "1" -> itemsList[indexOfItem.toInt() - 1].take()
                    "2" -> itemsList[indexOfItem.toInt() - 1].read()
                    "3" -> itemsList[indexOfItem.toInt() - 1].getDetailedInformation()
                    "4" -> itemsList[indexOfItem.toInt() - 1].takeBack()
                    "0" -> return false
                    else -> println("Ошибка ввода, повторите попытку")
                }
            }
        } catch (someException: Throwable) {
            println("Ошибка ввода, повторите попытку")
        }
       return true
    }
}


abstract class Library(val id: Int, var accessibility: Boolean, val name: String): Returnable, Readable, Takeable {

    abstract  fun getDetailedInformation()

    fun getSummaryInformation(): String {
        return "$name доступна: ${if (accessibility) "Да" else "Нет" }"
    }

}

class Book(id: Int, accessibility: Boolean, name: String, private val pages: Int, private val author: String): Library(id, accessibility, name) {
    override fun getDetailedInformation() {
        println("книга: $name ($pages стр.) автора: $author с id: $id доступна: ${if (accessibility) "Да" else "Нет" }")
    }

    override fun takeBack() {
        if (!accessibility) {
            println("Книгу $id вернули в библиотеку")
            accessibility = true
        } else {
            println("Действие невозможно, объект присутствует в библиотеке")
        }
    }

    override fun read() {
        if (accessibility){
            println("Книгу $id взяли в читальный зал")
            accessibility = false
        } else {
            println("Действие невозможно, объект недоступен")
        }
    }

    override fun take() {
        if (accessibility){
            println("Книгу $id взяли домой")
            accessibility = false
        } else {
            println("Действие невозможно, объект недоступен")
        }
    }
}


class Newspaper(id: Int, accessibility: Boolean, name: String, private val releaseNumber: Int) : Library(id, accessibility, name), Readable {
    override fun getDetailedInformation() {
        println("выпуск: $releaseNumber газеты $name с id: $id доступен: ${if (accessibility) "Да" else "Нет" }")
    }

    override fun takeBack() {
        if (!accessibility) {
            println("Газету $id вернули в библиотеку")
            accessibility = true
        } else {
            println("Действие невозможно, объект присутствует в библиотеке")
        }
    }

    override fun read() {
        if (accessibility){
            println("Газету $id взяли в читальный зал")
            accessibility = false
        } else {
            println("Действие невозможно, объект недоступен")
        }
    }

    override fun take() {
        println("Действие невозможно, газету нельзя взять с собой")
    }
}


class Disc(id: Int, accessibility: Boolean, name: String, private val type: String) : Library(id, accessibility, name), Takeable {
    override fun getDetailedInformation() {
        println("$type $name достуен: ${if (accessibility) "Да" else "Нет" }")
    }

    override fun takeBack() {
        if (!accessibility) {
            println("Диск $id вернули в библиотеку")
            accessibility = true
        } else {
            println("Действие невозможно, объект присутствует в библиотеке")
        }
    }

    override fun read() {
        println("Действие невозможно, диск нельзя читать в зале")
    }

    override fun take() {
        if (accessibility){
            println("Диск $id взяли домой")
            accessibility = false
        } else {
            println("Действие невозможно, объект недоступен")
        }
    }
}

interface Readable {
    fun read()
}

interface Takeable {
    fun take()
}

interface Returnable {
    fun takeBack()
}
