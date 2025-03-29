package library

import com.example.library.R

data class Book(override val imageId: Int, override val id: Int, override var isAvailable: Boolean, override val name: String, private val pages: Int, private val author: String): Library(id, isAvailable, name, imageId) {
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