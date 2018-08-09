package pro.retor.mymvpv2.base.presenter

import android.os.Parcelable
import android.support.annotation.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import pro.retor.mymvpv2.base.model.BaseModel
import pro.retor.mymvpv2.base.view.BaseView

abstract class Presenter<M : BaseModel, V : BaseView>(private val model: M) {
    private val subscriptions = CompositeDisposable()

    protected fun addSubscription(@NonNull subscription: Disposable) {
        subscriptions.add(subscription)
    }

    fun addArguments(arguments: Any) {

    }

    abstract fun onViewAttached(@NonNull view: V)

    fun onViewDetached() {
        subscriptions.clear()
    }

    fun saveModel(): Parcelable {
        return model.writeModelToParcel()
    }

    fun restoreModel(state: Parcelable) {
        model.readFromParcel(state)
    }

    fun reset(){}
}