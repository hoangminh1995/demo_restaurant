package com.demorestaurant.data.type

import com.google.gson.annotations.SerializedName

enum class OperationState{
    @SerializedName("OPEN")
    OPEN,
    @SerializedName("CLOSED")
    CLOSED
}