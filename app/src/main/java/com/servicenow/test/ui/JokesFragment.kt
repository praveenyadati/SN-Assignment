package com.servicenow.test.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.servicenow.baseframework.ui.BaseFragment
import com.servicenow.test.R
import com.servicenow.test.databinding.FragmentJokesBinding
import com.servicenow.test.utils.Constants

class JokesFragment : BaseFragment<FragmentJokesBinding>(R.layout.fragment_jokes) {

    override fun setUpFragmentUI(data: Intent?, savedInstanceState: Bundle?, view: View?) {
        hideProgress()
        val args: JokesFragmentArgs by navArgs()
        val data = args.data
        val adapter = JokesAdapter(data, context)
        val viewPager = viewDataBinding.viewPager
        viewPager.adapter = adapter
        viewPager.currentItem = data.size - 1
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(Constants.POSITION, viewDataBinding.viewPager.currentItem)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if(savedInstanceState != null)
            viewDataBinding.viewPager.currentItem = savedInstanceState.getInt(Constants.POSITION, 0)
    }

}