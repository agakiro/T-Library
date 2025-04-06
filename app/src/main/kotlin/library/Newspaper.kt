package library

import com.example.library.R

data class Newspaper(override val imageId: Int, override val id: Int, override var isAvailable: Boolean, override val name: String, val releaseNumber: Int, val releaseMonth: String) : Library(id, isAvailable, name, imageId),
    Readable {
    override fun getDetailedInformation(): String {
        return "Газета $name\nВыпуск: $releaseNumber\nМесяц выпуска: $releaseMonth\nid: $id\nДоступна: ${if (isAvailable) "Да" else "Нет" }"
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