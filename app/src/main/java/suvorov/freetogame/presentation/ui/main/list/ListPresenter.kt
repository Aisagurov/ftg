package suvorov.freetogame.presentation.ui.main.list

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableCompletableObserver
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import suvorov.freetogame.domain.entity.Game
import suvorov.freetogame.domain.interactor.GamesInteractor
import suvorov.freetogame.presentation.mvp.MvpPresenter
import suvorov.freetogame.util.Constants.REMOVE_GAME
import suvorov.freetogame.util.Constants.ADD_GAME
import javax.inject.Inject

class ListPresenter @Inject constructor(
    private val interactor: GamesInteractor
    ): MvpPresenter<ListView> {

    private var view: ListView? = null

    private var compositeDisposables = CompositeDisposable()

    fun getGames() {
        view?.showErrorMessage(false)
        view?.showProgress(true)
        val compositeDisposable = interactor.getGames().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<List<Game>>() {
                override fun onNext(t: List<Game>) {
                    if(t.isNotEmpty()) view?.showProgress(false)
                    view?.showGames(t)
                }
                override fun onError(e: Throwable) {
                    view?.showErrorMessage(true)
                    view?.showProgress(false)
                    view?.showError(e.toString())
                }
                override fun onComplete() {
                }
            })
        compositeDisposables.add(compositeDisposable)
    }

    fun addGameToLibrary(game: Game) {
        val compositeDisposable = interactor.addGameToLibrary(game).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableCompletableObserver() {
                override fun onComplete() {
                    view?.showMessage("${game.title} $ADD_GAME")
                }
                override fun onError(e: Throwable) {
                    e.message
                }
            })
        compositeDisposables.add(compositeDisposable)
    }

    fun removeGameFromLibrary(game: Game) {
        val compositeDisposable = interactor.removeGameFromLibrary(game).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableCompletableObserver() {
                override fun onComplete() {
                    view?.showMessage("${game.title} $REMOVE_GAME")
                }
                override fun onError(e: Throwable) {
                    e.message
                }
            })
        compositeDisposables.add(compositeDisposable)
    }

    override fun attachView(mvpView: ListView) {
        this.view = mvpView
    }

    override fun detachView() {
        compositeDisposables.clear()
        view = null
    }
}