package pro.retor.mymvpv2.application.base

import android.app.Dialog
import android.os.Bundle
import io.reactivex.Observable
import io.reactivex.android.MainThreadDisposable
import io.reactivex.functions.Consumer
import pro.retor.mymvpv2.application.internal.InternalDialogFragment
import pro.retor.mymvpv2.base.model.BaseModel
import pro.retor.mymvpv2.base.presenter.Presenter
import pro.retor.mymvpv2.base.view.BaseView
import pro.retor.mymvpv2.di.fragment.FragmentComponent
import java.util.*
import javax.inject.Inject

/**
 * Created by d.sokolov on 28.10.2017.
 */

abstract class BaseDialogFragment<M : BaseModel, V : BaseView, P : Presenter<M, V>>  : InternalDialogFragment<M, V, P>() {

    @Inject
    protected lateinit var pres: P
    private val resumeListener = ArrayList<BaseFragment.ResumeListener>()
    private var resumed: Boolean = false

    protected abstract fun getComponent(): FragmentComponent<V>

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        resumed = savedInstanceState != null
        return super.onCreateDialog(savedInstanceState)
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
            resumeListener.add(object : BaseFragment.ResumeListener {
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
