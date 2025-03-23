
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
