package suvorov.freetogame.presentation.ui.main.library

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import suvorov.freetogame.domain.entity.Game
import suvorov.freetogame.databinding.FragmentLibraryBinding
import suvorov.freetogame.presentation.App
import suvorov.freetogame.presentation.adapter.GamesAdapter
import suvorov.freetogame.presentation.common.OnGameClickListener
import suvorov.freetogame.presentation.common.OnLibraryClickListener
import suvorov.freetogame.presentation.ui.detail.DetailActivity
import suvorov.freetogame.util.Constants
import javax.inject.Inject

class LibraryFragment: Fragment(), OnGameClickListener, OnLibraryClickListener, LibraryView {

    private var _binding: FragmentLibraryBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var presenter: LibraryPresenter

    private var libraryAdapter = GamesAdapter(this, this)

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().application as App).appComponent.injectLibraryFragment(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLibraryBinding.inflate(inflater, container, false)
        presenter.attachView(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        presenter.getGamesLibrary()
    }

    private fun initView() {
        binding.libraryRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = libraryAdapter
        }
    }

    override fun showGames(games: List<Game>) {
        libraryAdapter.updateList(games)
    }

    override fun showMessage(text: String) {
        Toast.makeText(requireActivity(), text, Toast.LENGTH_LONG).show()
    }

    override fun showTextLibraryEmpty(show: Boolean) {
        if (show) binding.libraryTexView.visibility = View.VISIBLE
        else binding.libraryTexView.visibility = View.GONE
    }

    override fun onGameClick(id: Int) {
        requireActivity().run {
            startActivity(
                Intent(this, DetailActivity::class.java)
                    .apply {
                        putExtra(Constants.GAME_ID, id)
                    }
            )
        }
    }

    override fun onLibraryClick(game: Game) {
        presenter.removeGameFromLibrary(game)
        libraryAdapter.updateGameInLibrary(game)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
        _binding = null
    }
}