package com.demorestaurant.data.remote

import android.os.Parcelable
import com.demorestaurant.data.type.OperationState
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RestaurantResponse(
    @SerializedName("id")
    @Expose
    val id: String,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("image")
    @Expose
    val image: String,
    @SerializedName("operation_state")
    @Expose
    val operationState: OperationState
) : Parcelable