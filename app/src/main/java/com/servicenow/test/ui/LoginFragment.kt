package com.servicenow.test.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.servicenow.baseframework.ui.BaseFragment
import com.servicenow.test.R
import com.servicenow.test.databinding.FragmentLoginBinding
import com.servicenow.test.viewmodel.JokesViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    private val viewModel: JokesViewModel by sharedViewModel()

    override fun setUpFragmentUI(data: Intent?, savedInstanceState: Bundle?, view: View?) {
        initViewModel()
        setupButtonClick()
    }

    private fun initViewModel() {

        viewModel.init()
        viewDataBinding.model = viewModel

        viewModel.showLoading.observe(this, { showLoading ->
            if (showLoading) showProgress() else hideProgress()
        })

        viewModel.resultResponse.observe(this, { jokesList ->
            if (jokesList != null && jokesList.size > 0) {
                val action = LoginFragmentDirections.actionLoginFragmentToJokesFragment(jokesList)
                findNavController().navigate(action)
            } else {
                Toast.makeText(context, "Network Error", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun setupButtonClick() {
        viewModel.getLoginFields()!!.observe(this, { pair ->
            viewModel.getJokes(pair.first, pair.second)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
    }
}