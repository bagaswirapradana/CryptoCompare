package id.bagaswirapradana.test.stockbit.ui.home.view

import android.graphics.Color
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.squareup.picasso.Picasso
import id.bagaswirapradana.test.stockbit.R
import id.bagaswirapradana.test.stockbit.utils.KotlinEpoxyHolder


/**
 * Created by bagaswirapradana on 3/16/21.
 */
@EpoxyModelClass(layout = R.layout.item_coin)
abstract class CoinView : EpoxyModelWithHolder<CoinView.Holder>() {

    @EpoxyAttribute
    lateinit var name: String

    @EpoxyAttribute
    lateinit var fullname: String

    @EpoxyAttribute
    lateinit var url: String

    @EpoxyAttribute
    lateinit var price: String

    @EpoxyAttribute
    lateinit var change24Hours: String

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.name.text = name
        holder.fullName.text = fullname
        Picasso.get().load("https://www.cryptocompare.com$url").fit().into(holder.image)
        holder.price.text = price
        if (!change24Hours.contains("-")) {
            holder.change24Hours.text = "+$change24Hours"
        } else {
            holder.change24Hours.text = change24Hours
        }
        if (change24Hours.contains("-")) {
            holder.change24Hours.setTextColor(Color.parseColor("#e53935"))
        } else {
            holder.change24Hours.setTextColor(Color.parseColor("#008B00"))
        }
    }

    inner class Holder : KotlinEpoxyHolder() {
        val name by bind<TextView>(R.id.name_coin)
        val fullName by bind<TextView>(R.id.full_name_coin)
        val image by bind<AppCompatImageView>(R.id.image_coin)
        val price by bind<TextView>(R.id.price_coin)
        val change24Hours by bind<TextView>(R.id.change24hour_coin)
    }
}