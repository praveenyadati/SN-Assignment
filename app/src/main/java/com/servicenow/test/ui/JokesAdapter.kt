package com.servicenow.test.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.appcompat.widget.AppCompatTextView
import androidx.viewpager.widget.PagerAdapter
import com.servicenow.test.R
import com.servicenow.test.model.Joke

class JokesAdapter(private var models: Array<out Joke>, private val context: Context?) : PagerAdapter() {

    override fun getCount(): Int {
        return models.size
    }

    fun add(joke : Joke) {
        val list: MutableList<Joke> = models.toMutableList()
        list.add(joke)
        models = list.toTypedArray()
        notifyDataSetChanged()
    }

    @NonNull
    override fun instantiateItem(@NonNull container: ViewGroup, position: Int): Any {
        val layoutInflater = LayoutInflater.from(context)
        val view: View = layoutInflater.inflate(R.layout.layout_card_joke, container, false)
        val description : AppCompatTextView = view.findViewById(R.id.text_joke)
        description.setText(models.get(position).joke)
        container.addView(view)
        return view
    }

    override fun destroyItem(@NonNull container: ViewGroup, position: Int, @NonNull `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view.equals(`object`)
    }

}