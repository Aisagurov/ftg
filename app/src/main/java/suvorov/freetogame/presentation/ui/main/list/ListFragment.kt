package suvorov.freetogame.presentation.ui.main.list

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import suvorov.freetogame.R
import suvorov.freetogame.domain.entity.Game
import suvorov.freetogame.databinding.FragmentListBinding
import suvorov.freetogame.presentation.App
import suvorov.freetogame.presentation.adapter.GamesAdapter
import suvorov.freetogame.presentation.common.OnGameClickListener
import suvorov.freetogame.presentation.common.OnLibraryClickListener
import suvorov.freetogame.presentation.ui.detail.DetailActivity
import suvorov.freetogame.util.Constants.GAME_ID
import suvorov.freetogame.util.KeyboardHelper
import javax.inject.Inject

class ListFragment:
    Fragment(),
    OnGameClickListener,
    OnLibraryClickListener,
    SearchView.OnQueryTextListener,
    ListView {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var presenter: ListPresenter

    private val listAdapter = GamesAdapter(this, this)

    private var gamesList = listOf<Game>()

    private var searchRequest = ""

    private var sortPosition = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().application as App).appComponent.injectListFragment(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        presenter.attachView(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        initSearchView()

        initSortSpinner()

        binding.gameSearchView.setOnQueryTextListener(this)

        binding.errorTextView.setOnClickListener {
            presenter.getGames()
        }
    }

    private fun initView() {
        binding.gamesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
        }
    }

    private fun initSearchView() {
        binding.gameSearchView.apply {
            findViewById<TextView>(androidx.appcompat.R.id.search_src_text)
                .setHintTextColor(ContextCompat.getColor(requireActivity(), R.color.grey))

            findViewById<TextView>(androidx.appcompat.R.id.search_src_text).setTextColor(Color.WHITE)

            findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn).setColorFilter(Color.WHITE)
        }
    }

    private fun initSortSpinner() {
        val sourceLanguageAdapter = ArrayAdapter.createFromResource(
            requireActivity(), R.array.sort, R.layout.spinner_item)

        sourceLanguageAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        binding.sortSpinner.adapter = sourceLanguageAdapter

        binding.sortSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                presenter.getGames()
                sortPosition = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    override fun showProgress(show: Boolean) {
        if (show) binding.progressBar.visibility = View.VISIBLE
        else binding.progressBar.visibility = View.GONE
    }

    override fun showMessage(text: String) {
        Toast.makeText(requireActivity(), text, Toast.LENGTH_LONG).show()
    }

    override fun showErrorMessage(show: Boolean) {
        if (show) binding.errorTextView.visibility = View.VISIBLE
        else binding.errorTextView.visibility = View.GONE
    }

    override fun showError(error: String) {
        Log.e("error", error)
    }

    override fun showGames(games: List<Game>) {
        listAdapter.filterList(games, sortPosition, searchRequest)
        gamesList = games
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            listAdapter.filterList(gamesList, sortPosition, newText)
            searchRequest = newText
        }
        return true
    }

    override fun onGameClick(id: Int) {
        requireActivity().run {
            startActivity(
                Intent(this, DetailActivity::class.java)
                    .apply {
                        putExtra(GAME_ID, id)
                    }
            )
        }
    }

    override fun onLibraryClick(game: Game) {
        if (!game.isLibrary) presenter.addGameToLibrary(game)
        else presenter.removeGameFromLibrary(game)

        KeyboardHelper.hideKeyboard(requireActivity())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
        _binding = null
    }
}