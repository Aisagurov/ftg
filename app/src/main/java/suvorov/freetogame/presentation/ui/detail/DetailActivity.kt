package suvorov.freetogame.presentation.ui.detail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import suvorov.freetogame.R
import suvorov.freetogame.domain.entity.Game
import suvorov.freetogame.databinding.ActivityDetailBinding
import suvorov.freetogame.presentation.App
import suvorov.freetogame.util.Constants.GAME_ID
import suvorov.freetogame.util.setImage
import javax.inject.Inject

class DetailActivity: AppCompatActivity(), DetailView {

    private lateinit var binding: ActivityDetailBinding

    @Inject
    lateinit var presenter: DetailPresenter

    private var gameId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (application as App).appComponent.injectDetailActivity(this)

        presenter.attachView(this)

        if (intent.hasExtra(GAME_ID)) {
            gameId = intent.getIntExtra(GAME_ID, 0)
        }

        presenter.getGame(gameId!!)

        binding.arrowBack.setOnClickListener {
            finish()
        }
    }

    override fun showGame(game: Game) {
        binding.apply {
            titleTextView.text = game.title
            thumbnailImageView.setImage(game.thumbnail)
            shortDescriptionTextView.text = game.shortDescription
            gameUrlButton.text = game.gameUrl
            genreTextView.text = game.genre
            platformTextView.text = game.platform
            publisherTextView.text = game.publisher
            developerTextView.text = game.developer
            releaseDateTextView.text = game.releaseDate

            libraryImageView.setImageResource(
                if (game.isLibrary) R.drawable.footer
                else R.drawable.footer_border
            )

            libraryImageView.setOnClickListener {
                if (!game.isLibrary) presenter.addGameToLibrary(game)
                else presenter.removeGameFromLibrary(game)
            }
        }
    }

    override fun showMessage(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}