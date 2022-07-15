package suvorov.freetogame.util

import android.widget.ImageView
import com.squareup.picasso.Picasso
import suvorov.freetogame.R

fun ImageView.setImage(uri: String?) {
    Picasso.with(this.context)
        .load(uri)
        .placeholder(R.drawable.image_loaded)
        .error(R.drawable.image_not_found)
        .into(this)
}