package com.servicenow.baseframework.ui

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.airbnb.lottie.LottieAnimationView
import com.servicenow.baseframework.R

class LoadingView : DialogFragment() {

    private lateinit var activity: AppCompatActivity

    companion object {
        fun newInstance(): LoadingView {
            val loadingView = LoadingView()
            loadingView.setCancelable(false)
            return loadingView
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as AppCompatActivity
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(activity, R.style.ProgressDialog)
        val view: View = LayoutInflater.from(activity).inflate(R.layout.fragment_loading, null)
        val lottieAnimationView =
            view.findViewById<LottieAnimationView>(R.id.view_animation_loading)
        lottieAnimationView.setAnimation("loading.json")
        lottieAnimationView.playAnimation()
        dialog.setContentView(view)
        dialog!!.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(true)
        dialog.window.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        );
        return dialog
    }

    override fun onDestroy() {
        super.onDestroy()
        dismissAllowingStateLoss()
    }

}