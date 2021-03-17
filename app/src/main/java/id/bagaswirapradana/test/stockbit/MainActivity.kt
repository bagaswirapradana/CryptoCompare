package id.bagaswirapradana.test.stockbit

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import de.greenrobot.event.EventBus
import id.bagaswirapradana.test.stockbit.databinding.MainActivityBinding
import id.bagaswirapradana.test.stockbit.event.ReselectedEvent


class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment)
        binding.navView.setupWithNavController(navController)

        binding.navView.setOnNavigationItemReselectedListener {
            val destination: NavDestination = navController.currentDestination!!
            when (destination.id) {
                R.id.navigation_home -> {
                    EventBus.getDefault().post(ReselectedEvent(0))
                }
                else -> {
                    NavigationUI.onNavDestinationSelected(it, navController)
                }
            }
        }

        showLogin()
    }

    private fun showLogin() {
        val view = layoutInflater.inflate(R.layout.login_fragment, null)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(view)
        dialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { item ->
                val behaviour = BottomSheetBehavior.from(item)
                behaviour.addBottomSheetCallback(object :
                    BottomSheetBehavior.BottomSheetCallback() {

                    override fun onSlide(bottomSheet: View, slideOffset: Float) {}

                    override fun onStateChanged(bottomSheet: View, newState: Int) {
                        when (newState) {
                            BottomSheetBehavior.STATE_HIDDEN -> {
                            }
                            BottomSheetBehavior.STATE_COLLAPSED -> {
                                dialog.dismiss()
                            }
                            BottomSheetBehavior.STATE_DRAGGING -> {
                            }
                            BottomSheetBehavior.STATE_EXPANDED -> {
                            }
                            BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
                            }
                            BottomSheetBehavior.STATE_SETTLING -> {
                            }
                        }
                    }
                })
                setupFullHeight(item)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        dialog.show()
    }

    private fun setupFullHeight(it: View) {
        val layoutParams = it.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        it.layoutParams = layoutParams
    }
}