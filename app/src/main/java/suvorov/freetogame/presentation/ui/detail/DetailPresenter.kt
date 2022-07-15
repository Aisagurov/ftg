package suvorov.freetogame.presentation.ui.detail

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

class DetailPresenter @Inject constructor(
    private val interactor: GamesInteractor
    ): MvpPresenter<DetailView> {

    private var view: DetailView? = null

    private var compositeDisposables = CompositeDisposable()

    fun getGame(id: Int) {
        val compositeDisposable = interactor.getGameFromId(id).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<Game>() {
                override fun onNext(t: Game) {
                    view?.showGame(t)
                }
                override fun onError(e: Throwable) {
                    e.message
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
                    view?.showMessage("${game.title} ${Constants.ADD_GAME}")
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
                    view?.showMessage("${game.title} ${Constants.REMOVE_GAME}")
                }
                override fun onError(e: Throwable) {
                    e.message
                }
            })
        compositeDisposables.add(compositeDisposable)
    }

    override fun attachView(mvpView: DetailView) {
        this.view = mvpView
    }

    override fun detachView() {
        compositeDisposables.clear()
        view = null
    }
}