package suvorov.freetogame.presentation.mvp

interface MvpPresenter<V : MvpView> {
    fun attachView(mvpView: V)
    fun detachView()
}