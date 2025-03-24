package forManaging

class Manager {
    fun <T> buy(shops: Shops<T>): T{
        return shops.sell()
    }
}
