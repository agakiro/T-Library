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

    fun manageManager() {
        println("*Отображён ассортимент магазина*")
        while (true){
            println("""
            Выберите предмет для покупки:
            1 - Книга
            2 - Газета
            3 - Диск
            0 - Вернуться в меню
        """.trimIndent())
            when (readln()) {
                "1" -> println("Книга ${Manager().buy(BookShop()).name} была куплена")
                "2" -> println("Газета ${Manager().buy(NewspaperShop()).name} была куплена")
                "3" -> println("Диск ${Manager().buy(DiscShop()).name} был куплен")
                "0" -> return
                else -> println("Ошибка при вводе, попробуйте ещё раз")
            }
        }
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
