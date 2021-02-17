package com.servicenow.test.ui


import android.content.Intent
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.servicenow.baseframework.ui.BaseActivity
import com.servicenow.baseframework.ui.isConnected
import com.servicenow.test.R
import com.servicenow.test.databinding.ActivityMainBinding
import com.servicenow.test.viewmodel.JokesViewModel
import org.koin.androidx.viewmodel.ext.android.getStateViewModel


class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    lateinit var savedVm: JokesViewModel

    override fun setUpActivityUI(data: Intent, savedInstanceState: Bundle?) {
        savedVm  = getStateViewModel()
        connectionLiveData.observe(this) { savedVm.isNetworkAvailable.value = it }
        savedVm.isNetworkAvailable.value = isConnected
        val host = NavHostFragment.create(R.navigation.app_nav_graph)
        supportFragmentManager.beginTransaction().replace(viewDataBinding.fragmentContainer.id, host).setPrimaryNavigationFragment(host).commit()
    }
}
