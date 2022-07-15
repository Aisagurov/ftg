package suvorov.freetogame.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import suvorov.freetogame.R
import suvorov.freetogame.domain.entity.Game
import suvorov.freetogame.databinding.ItemListBinding
import suvorov.freetogame.presentation.common.OnGameClickListener
import suvorov.freetogame.presentation.common.OnLibraryClickListener
import suvorov.freetogame.util.setImage
import java.util.*

class GamesAdapter(
    private val onGameClickListener:OnGameClickListener,
    private val onLibraryClickListener: OnLibraryClickListener
    ): RecyclerView.Adapter<GamesAdapter.GamesViewHolder>() {

    private val games = arrayListOf<Game>()

    companion object {
        const val SPINNER_POSITION_RELEVANCE = 0
        const val SPINNER_POSITION_ALPHABETICAL = 1
        const val SPINNER_POSITION_RELEASE_DATE = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamesViewHolder {
        return GamesViewHolder(ItemListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: GamesViewHolder, position: Int) {
        holder.bind(games[position], onGameClickListener, onLibraryClickListener)
    }

    override fun getItemCount(): Int {
        return games.size
    }

    fun updateList(list: List<Game>) {
        games.clear()
        games.addAll(list)
        notifyDataSetChanged()
    }

    fun updateGameInLibrary(game: Game) {
        games.remove(game)
        notifyDataSetChanged()
    }

    fun filterList(list: List<Game>, position: Int, query: String) {
        games.clear()
        for (game in list) {
            if (game.title?.lowercase(Locale.ROOT)?.contains(query.lowercase(Locale.ROOT)) == true) {
                games.add(game)
            }
        }
        when(position) {
            SPINNER_POSITION_RELEVANCE -> games
            SPINNER_POSITION_ALPHABETICAL -> games.sortBy { it.title }
            SPINNER_POSITION_RELEASE_DATE -> games.sortByDescending { it.releaseDate }
        }
        notifyDataSetChanged()
    }

    class GamesViewHolder(private val binding: ItemListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(game: Game, onGameClickListener: OnGameClickListener, onLibraryClickListener: OnLibraryClickListener) {

            binding.apply {
                titleTextView.text = game.title
                shortDescriptionTextView.text = game.shortDescription
                genreTextView.text = game.genre
                thumbnailImageView.setImage(game.thumbnail)

                platformImageView.setImageResource(
                    if(game.platform == "Web Browser") R.drawable.browser
                    else R.drawable.windows
                )

                libraryImageView.setImageResource(
                    if (game.isLibrary) R.drawable.footer
                    else R.drawable.footer_border
                )

                libraryImageView.setOnClickListener {
                    onLibraryClickListener.onLibraryClick(game)
                }
            }

            itemView.setOnClickListener {
                onGameClickListener.onGameClick(game.id ?: 0)
            }
        }
    }
}