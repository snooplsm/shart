package us.wmwm.stroll.sample

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataItem(
        val showBackButton:Boolean,
        val string: String) : Parcelable