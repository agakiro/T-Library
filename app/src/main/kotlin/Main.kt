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