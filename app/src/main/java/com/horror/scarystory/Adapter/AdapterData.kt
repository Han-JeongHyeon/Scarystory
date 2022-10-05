package com.horror.scarystory.Adapter

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AdapterData(
    val Title : String,
    val value: Boolean,
    val tag : String,
    val position : Int
) : Parcelable
