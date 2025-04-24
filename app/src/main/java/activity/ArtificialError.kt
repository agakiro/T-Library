package activity

sealed class ArtificialError : Throwable() {
    abstract override val message: String?

    class LoadingError(override val message: String?): ArtificialError()
    class SavingError(override val message: String?): ArtificialError()
}