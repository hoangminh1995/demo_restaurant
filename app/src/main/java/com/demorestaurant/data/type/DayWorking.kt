package com.demorestaurant.data.type

import com.google.gson.annotations.SerializedName

enum class DayWorking {
    @SerializedName("MON")
    MON,

    @SerializedName("TUE")
    TUE,

    @SerializedName("WED")
    WED,

    @SerializedName("THU")
    THU,

    @SerializedName("FRI")
    FRI,

    @SerializedName("SAT")
    SAT,

    @SerializedName("SUN")
    SUN
}