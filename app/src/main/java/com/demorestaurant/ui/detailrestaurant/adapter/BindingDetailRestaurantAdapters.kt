package com.demorestaurant.ui.detailrestaurant.adapter

import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.demorestaurant.R
import com.demorestaurant.data.type.DayWorking


@BindingAdapter("dayWorking")
fun bindDayWorking(view: TextView, day: DayWorking) {
    when (day) {
        DayWorking.MON -> view.text = view.context.getString(R.string.monday)
        DayWorking.TUE -> view.text = view.context.getString(R.string.tuesday)
        DayWorking.WED -> view.text = view.context.getString(R.string.wednesday)
        DayWorking.THU -> view.text = view.context.getString(R.string.thursday)
        DayWorking.FRI -> view.text = view.context.getString(R.string.friday)
        DayWorking.SAT -> view.text = view.context.getString(R.string.saturday)
        DayWorking.SUN -> view.text = view.context.getString(R.string.sunday)
    }
}

@BindingAdapter("timeWorking")
fun bindTimeWorking(view: TextView, timeWorking: String?) {
    when {
        timeWorking != null -> view.text = timeWorking
        else -> {
            view.text = view.context.getString(R.string.closed)
            view.setTextColor(ContextCompat.getColor(view.context,android.R.color.holo_red_dark))
        }
    }
}