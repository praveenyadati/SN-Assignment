package com.servicenow.test.ui

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import androidx.viewpager.widget.ViewPager
import com.servicenow.baseframework.ui.BaseFragment
import com.servicenow.test.R
import com.servicenow.test.databinding.FragmentJokesBinding
import com.servicenow.test.model.Joke
import com.servicenow.test.utils.Constants
import com.servicenow.test.viewmodel.JokesViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class JokesFragment : BaseFragment<FragmentJokesBinding>(R.layout.fragment_jokes) {

    private var lastIndex = 0
    private var adapter: JokesAdapter? = null
    private var firstName : String? = null
    private var lastName : String? = null

    private val viewModel: JokesViewModel by sharedViewModel()

    override fun setUpFragmentUI(data: Intent?, savedInstanceState: Bundle?, view: View?) {
        setupViewPager()
        initViewModel()
        initSwipeListener()
    }

    private fun setupViewPager() {
        val args: JokesFragmentArgs by navArgs()
        val data = args.data
        firstName = args.firstName
        lastName = args.lastName
        lastIndex = data.size - 1
        adapter = JokesAdapter(data, context)
        val viewPager = viewDataBinding.viewPager
        viewPager.adapter = adapter
        viewPager.setCurrentItem(lastIndex)
        viewPager.addOnPageChangeListener(onPageChangeListener)
        activity?.supportActionBar?.setTitle("${getString(R.string.app_name)} ${lastIndex+1} / ${data.size}")
    }

    fun initViewModel() {
        viewModel.showLoading.observe(this, { showLoading ->
            if (showLoading) showProgress() else hideProgress()
        })

        viewModel.jokeData.observe(this, { joke ->
            if(joke != null) {
                adapter?.add(joke)
                lastIndex++
                viewDataBinding.viewPager.setCurrentItem(lastIndex, true)
            } else {
                Toast.makeText(context, getString(R.string.error), Toast.LENGTH_LONG).show()
            }
        })
    }

    fun initSwipeListener() {
        viewDataBinding.viewPager.setOnTouchListener(object : View.OnTouchListener {
            var downX : Float = 0F
            var upX : Float = 0F
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when(event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        downX = event.getX()
                        return true
                    }

                    MotionEvent.ACTION_UP -> {
                        upX = event.getX()
                        val deltaX = downX - upX
                        if (deltaX > Constants.MIN_SWIPE_DISTANCE && viewDataBinding.viewPager.currentItem == lastIndex) {
                            viewModel.getJokes(firstName, lastName, false)
                            return true
                        }
                        return false
                    }
                }
                return v?.onTouchEvent(event) ?: true
            }
        })
    }

    private val onPageChangeListener = object : ViewPager.OnPageChangeListener{
        override fun onPageScrollStateChanged(state: Int) {}
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
        override fun onPageSelected(position: Int) {
            activity.supportActionBar?.setTitle("${getString(R.string.app_name)} ${position+1} / ${adapter?.count}")
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(Constants.POSITION, viewDataBinding.viewPager.currentItem)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState != null) {
            val currentPosition = savedInstanceState.getInt(Constants.POSITION, lastIndex)
            viewDataBinding.viewPager.setCurrentItem(currentPosition)
            activity.supportActionBar?.setTitle("${getString(R.string.app_name)} ${currentPosition+1} / ${adapter?.count}")
        }
    }

}