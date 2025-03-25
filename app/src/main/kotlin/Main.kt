import extraFunction.Digitizer
import extraFunction.toType
import library.Book
import library.Library
import library.LibraryItemsCreator


fun main(){
    val action = Action()
    val digitizer1 = Digitizer()
    val itemsCreator = LibraryItemsCreator()

    val libraryItems = mutableListOf(
        itemsCreator.createBook(90743, true, "Маугли", 202, "Джозеф Киплинг"),
        itemsCreator.createBook(7, true, "Алхимик", 223, "Пауло Коэльо"),
        itemsCreator.createNewspaper(17245, true, "Сельская жизнь", 794, "Апрель"),
        itemsCreator.createNewspaper(4, true, "Комсомольская правда", 52, "Март"),
        itemsCreator.createDisc(5, true, "Дэдпул и Росомаха", "DVD"),
        itemsCreator.createDisc(111, true, "Bizkit", "CD"),
        digitizer1.digitize(itemsCreator.createBook(7, true, "Алхимик", 223, "Пауло Коэльо"))
    )
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
