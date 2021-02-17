package com.servicenow.baseframework.ui

import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import org.jetbrains.annotations.NotNull


abstract class BaseFragment<T : ViewDataBinding>(@LayoutRes val layoutResourceId: Int) : Fragment(layoutResourceId) {

    private lateinit var activity: BaseActivity<*>
    private var bundle: Bundle? = null
    private var intent: Intent? = null
    private lateinit var mRootView: View

    protected lateinit var viewDataBinding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = getActivity() as BaseActivity<*>
        bundle = arguments
        intent = activity.intent
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, layoutResourceId, container, false)
        mRootView = viewDataBinding.getRoot()
        setRetainInstance(true)
        mRootView.setClickable(true)
        mRootView.setFocusable(true)
        return mRootView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (bundle != null) intent!!.putExtras(bundle)
        setUpFragmentUI(intent, savedInstanceState, mRootView)
    }

    abstract fun setUpFragmentUI(data: Intent?, savedInstanceState: Bundle?, view: View?)

    fun dismissKeyboard(windowToken: IBinder) {
        activity.dismissKeyboard(windowToken)
    }

    fun showKeyboard(view: View) {
        activity.showKeyboard(view)
    }

    protected fun showProgress() {
       activity.showProgress()
    }

    protected fun hideProgress() {
        activity.hideProgress()
    }

    override fun onDestroyView() {
        if (mRootView.parent != null) {
            (mRootView.parent as ViewGroup).removeView(mRootView)
        }
        super.onDestroyView()
        hideProgress()
    }


}