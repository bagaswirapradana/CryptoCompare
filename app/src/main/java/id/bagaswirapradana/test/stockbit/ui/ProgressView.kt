package id.bagaswirapradana.test.stockbit.ui

import android.widget.ProgressBar
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import id.bagaswirapradana.test.stockbit.R
import id.bagaswirapradana.test.stockbit.utils.KotlinEpoxyHolder

/**
 * Created by bagaswirapradana on 3/17/21.
 */
@EpoxyModelClass(layout = R.layout.item_loading)
abstract class ProgressView : EpoxyModelWithHolder<ProgressView.Holder>() {

    class Holder : KotlinEpoxyHolder() {
        val name by bind<ProgressBar>(R.id.progress_bar)
    }
}