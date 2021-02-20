package com.servicenow.test.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.BindingAdapter
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
        initListeners()
    }

    fun initListeners() {
        viewDataBinding.etFirstName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                viewModel.notifyFirstName()
            }
        })

        viewDataBinding.etLastName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                viewModel.notifyLastName()
            }
        })
    }

    private fun initViewModel() {
        viewModel.init()
        viewDataBinding.model = viewModel

        viewModel.showLoading.observe(this, { showLoading ->
            if (showLoading) showProgress() else hideProgress()
        })

        viewModel.jokesListData.observe(this, { jokesList ->
            if (jokesList != null && jokesList.size > 0) {
                val action = LoginFragmentDirections.actionLoginFragmentToJokesFragment(jokesList, viewModel.firstName, viewModel.lastName)
                findNavController().navigate(action)
            } else {
                Toast.makeText(context, getString(R.string.error), Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun setupButtonClick() {
        viewModel.getLoginFields()!!.observe(this, { pair ->
            viewModel.getJokes(pair.first, pair.second,true)
        })
    }

}

@BindingAdapter("android:text")
fun setStringWIthSelection(view: EditText, str: String?) {
    val updateTextLength = if (str == null) 0 else str.length
    view.setText(str)
    view.setSelection(updateTextLength)
}
