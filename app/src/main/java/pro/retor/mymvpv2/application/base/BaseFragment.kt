package pro.retor.mymvpv2.application.base

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.Observable
import io.reactivex.android.MainThreadDisposable
import io.reactivex.functions.Consumer
import pro.retor.mymvpv2.application.internal.InternalFragment
import pro.retor.mymvpv2.base.model.BaseModel
import pro.retor.mymvpv2.base.presenter.Presenter
import pro.retor.mymvpv2.base.view.BaseView
import pro.retor.mymvpv2.di.fragment.FragmentComponent
import java.util.*
import javax.inject.Inject

abstract class BaseFragment<M : BaseModel, V : BaseView, P : Presenter<M, V>> : InternalFragment<M, V, P>() {

    @Inject
    protected lateinit var pres: P
    private val resumeListener = ArrayList<ResumeListener>()
    private var resumed: Boolean = false

    protected abstract fun getComponent(): FragmentComponent<V>

    override fun getPresenter(): P {
        return pres
    }

    @get:LayoutRes
    protected abstract val layout: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (savedInstanceState != null)
            resumed = true
        return inflater.inflate(layout, container, false)
    }

    override fun onResume() {
        super.onResume()
        if (!resumeListener.isEmpty())
            for (l in resumeListener) {
                l.onResume()
            }
    }

    fun resumed(): Observable<Boolean> {
        return Observable.create { sub ->
            resumeListener.add(object : ResumeListener {
                override fun onResume() {
                    if (sub != null && !sub.isDisposed)
                        sub.onNext(resumed)
                }
            })
            sub.setDisposable(object : MainThreadDisposable() {
                override fun onDispose() {
                    resumeListener.clear()
                }
            })
        }
    }

    fun showError(): Consumer<Throwable> {
        return Consumer {
            showError(it)
        }
    }

    interface ResumeListener {
        fun onResume()
    }
}
