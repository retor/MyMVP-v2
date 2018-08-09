package pro.retor.mymvpv2.base.model

import android.os.Parcelable

interface StateMapper<S, P : Parcelable> {

    fun mapToState(parcel: P): S

    fun mapToParcel(state: S): P
}