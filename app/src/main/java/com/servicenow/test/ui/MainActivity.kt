package com.servicenow.test.ui


import android.content.Intent
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import com.servicenow.baseframework.ui.BaseActivity
import com.servicenow.baseframework.ui.isConnected
import com.servicenow.test.R
import com.servicenow.test.databinding.ActivityMainBinding
import com.servicenow.test.viewmodel.JokesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    val viewModel: JokesViewModel by viewModel(JokesViewModel::class)

    private var isDisConnected : Boolean = false;

    override fun setUpActivityUI(data: Intent, savedInstanceState: Bundle?) {
        connectionLiveData.observe(this) {
            viewModel.isNetworkAvailable.value = it
            val snackbarMsg : String? = if(it && isDisConnected) getString(R.string.network_success)
                                else if(!it) getString(R.string.network_error) else null
            if(snackbarMsg != null) {
                val snackBar : Snackbar = Snackbar.make(viewDataBinding.fragmentContainer,
                    snackbarMsg,
                    Snackbar.LENGTH_LONG)
                val bgColor : Int = if(!it) android.R.color.holo_red_light else android.R.color.holo_green_light
                snackBar.view.setBackgroundColor(ContextCompat.getColor(this, bgColor))
                snackBar.show()
            }
            isDisConnected = !it
        }
        viewModel.isNetworkAvailable.value = isConnected
        val host = NavHostFragment.create(R.navigation.app_nav_graph)
        supportFragmentManager.beginTransaction().replace(
            viewDataBinding.fragmentContainer.id, host
        ).setPrimaryNavigationFragment(host).commit()
    }
}
