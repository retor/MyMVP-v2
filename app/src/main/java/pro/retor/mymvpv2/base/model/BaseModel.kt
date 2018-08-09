package pro.retor.mymvpv2.base.model

import android.os.Parcelable

interface BaseModel {
    fun readFromParcel(parcelable: Parcelable)

    fun writeModelToParcel(): Parcelable
}