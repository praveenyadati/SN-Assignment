package com.servicenow.baseframework.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentManager

abstract class BaseActivity<T : ViewDataBinding>(@LayoutRes val layoutResourceId: Int) :
    AppCompatActivity() {

    private var mProgressDialog: LoadingView? = null

    protected lateinit var viewDataBinding: T

    protected lateinit var connectionLiveData: ConnectionLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fm: FragmentManager = getSupportFragmentManager()
        for (i in 0 until fm.backStackEntryCount) {
            fm.popBackStack()
        }
        connectionLiveData = ConnectionLiveData(this)
        viewDataBinding = DataBindingUtil.setContentView(this, layoutResourceId)
        setUpActivityUI(intent, savedInstanceState)
    }

    abstract fun setUpActivityUI(data: Intent, savedInstanceState: Bundle?)

    open fun showProgress() {
        if (mProgressDialog != null) {
            return
        }
        mProgressDialog = LoadingView.newInstance()
        mProgressDialog!!.show(supportFragmentManager, "progress")
    }

    fun hideProgress() {
        if (mProgressDialog != null) {
            mProgressDialog!!.dismissAllowingStateLoss()
            mProgressDialog = null
        }
    }

    fun dismissKeyboard(windowToken: IBinder) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    fun showKeyboard(view: View) {
        (view as? AppCompatEditText)?.requestFocus()
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(
            InputMethodManager.SHOW_FORCED,
            InputMethodManager.HIDE_IMPLICIT_ONLY
        )
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}