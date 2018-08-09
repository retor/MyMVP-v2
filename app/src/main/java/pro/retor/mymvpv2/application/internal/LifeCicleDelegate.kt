package pro.retor.mymvpv2.application.internal

import android.os.Bundle
import android.os.Parcelable
import android.support.annotation.NonNull
import pro.retor.mymvpv2.base.model.BaseModel
import pro.retor.mymvpv2.base.presenter.Presenter
import pro.retor.mymvpv2.base.view.BaseView
import java.lang.ref.WeakReference


internal class LifeCicleDelegate<M : BaseModel, V : BaseView, P : Presenter<M, V>> {

    private lateinit var presenter: WeakReference<P>
    private var SAVE_TAG: String = "empty"
    private lateinit var view: WeakReference<V>

    fun onCreate(@NonNull view: V, savedState: Bundle?, arg: Bundle?) {
        checkPresenter()
        this.view = WeakReference(view)
        presenter.get()?.let {
            SAVE_TAG = it::class.java.name
        }
        if (arg != null)
            presenter.get()?.addArguments(arg)
        if (savedState != null)
            presenter.get()?.restoreModel(savedState.getParcelable<Parcelable>(SAVE_TAG))
    }

    fun onStart() {
        checkPresenter()
        view.get()?.let {
            presenter.get()?.onViewAttached(it)
        }
    }

    fun onSavedState(outState: Bundle) {
        outState.putParcelable(SAVE_TAG, presenter.get()?.saveModel())
    }

    fun onStop() {
        checkPresenter()
        presenter.get()?.onViewDetached()
    }

    private fun checkPresenter() {
        if (presenter == null || presenter.get() == null)
            throw IllegalArgumentException("Presenter must be set first")
    }

    fun setPresenter(presenter: P) {
        this.presenter = WeakReference<P>(presenter)
    }

    fun onDestroy() {
        presenter.get()?.reset()
        presenter.clear()
        view.clear()
    }
}