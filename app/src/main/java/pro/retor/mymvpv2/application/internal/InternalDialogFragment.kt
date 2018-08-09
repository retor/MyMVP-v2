package pro.retor.mymvpv2.application.internal

import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v4.app.DialogFragment
import pro.retor.mymvpv2.base.model.BaseModel
import pro.retor.mymvpv2.base.presenter.Presenter
import pro.retor.mymvpv2.base.view.BaseView

abstract class InternalDialogFragment<M : BaseModel, V : BaseView, P : Presenter<M, V>> : DialogFragment(), BaseView {

    @NonNull
    private lateinit var delegate: LifeCicleDelegate<M, V, P>
    private var needUpdate: Boolean = false

    protected abstract val presenter: P

    val getBaseActivity: InternalActivity<*, *, *>
        get() = activity as InternalActivity<*, *, *>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
        delegate = LifeCicleDelegate()
        needUpdate = savedInstanceState == null
        delegate.setPresenter(presenter)
        delegate.onCreate(this as V, savedInstanceState, arguments)
    }

    override fun onStart() {
        super.onStart()
        delegate.onStart()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        delegate.onSavedState(outState)
    }

    override fun onStop() {
        delegate.onStop()
        super.onStop()
    }

    override fun onDestroy() {
        delegate.onDestroy()
        super.onDestroy()
    }

    protected abstract fun inject()
}
