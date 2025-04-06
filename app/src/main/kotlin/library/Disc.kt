package library

import com.example.library.R

data class Disc(override val imageId: Int, override val id: Int, override var isAvailable: Boolean, override val name: String, private val type: String) : Library(id, isAvailable, name, imageId),
    Takeable {
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