package com.demorestaurant.ui.listrestaurant.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.demorestaurant.R
import com.demorestaurant.data.type.OperationState


@BindingAdapter("operationState")
fun bindOperationState(view: TextView, operationState: OperationState) {
    when (operationState) {
        OperationState.CLOSED -> view.text = view.context.getString(R.string.closed)
        OperationState.OPEN -> view.text = view.context.getString(R.string.open)
    }
}
