package suvorov.freetogame.presentation.ui.main.library

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableCompletableObserver
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import suvorov.freetogame.domain.entity.Game
import suvorov.freetogame.domain.interactor.GamesInteractor
import suvorov.freetogame.presentation.mvp.MvpPresenter
import suvorov.freetogame.util.Constants
import javax.inject.Inject

class LibraryPresenter @Inject constructor(
    private val interactor: GamesInteractor
): MvpPresenter<LibraryView> {

    private var view: LibraryView? = null

    private var compositeDisposables = CompositeDisposable()

    fun getGamesLibrary() {
        val compositeDisposable = interactor.getGamesLibrary().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<List<Game>>() {
                override fun onNext(t: List<Game>) {
                    if (t.isNotEmpty()) view?.showTextLibraryEmpty(false)
                    else view?.showTextLibraryEmpty(true)
                    view?.showGames(t)
                }
                override fun onError(e: Throwable) {
                    e.message
                }
                override fun onComplete() {
                }
            })
        compositeDisposables.add(compositeDisposable)
    }

    fun removeGameFromLibrary(game: Game) {
        val compositeDisposable = interactor.removeGameFromLibrary(game).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableCompletableObserver() {
                override fun onComplete() {
                    view?.showMessage("${game.title} ${Constants.REMOVE_GAME}")
                }
                override fun onError(e: Throwable) {
                    e.message
                }
            })
        compositeDisposables.add(compositeDisposable)
    }

    override fun attachView(mvpView: LibraryView) {
        this.view = mvpView
    }

    override fun detachView() {
        compositeDisposables.clear()
        view = null
    }
}