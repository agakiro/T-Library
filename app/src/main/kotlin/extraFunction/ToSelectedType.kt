package extraFunction

inline fun <T, reified Result> toType(list: List<T>): MutableList<Result> {
    val res = mutableListOf<Result>()
    for (i in list) {
        if (i is Result) {
            res.add(i)
        }
    }
    return res
}