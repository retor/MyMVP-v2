package pro.retor.mymvpv2.application.internal

import android.os.Bundle
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.v7.app.AppCompatActivity
import android.view.View
import pro.retor.mymvpv2.application.application.BaseApplication
import pro.retor.mymvpv2.base.model.BaseModel
import pro.retor.mymvpv2.base.presenter.Presenter
import pro.retor.mymvpv2.base.view.BaseView


abstract class InternalActivity<M : BaseModel, V : BaseView, P : Presenter<M, V>> : AppCompatActivity(), BaseView {

    @NonNull
    private lateinit var delegate: LifeCicleDelegate<M, V, P>
    private var needUpdate: Boolean = false

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
        delegate = LifeCicleDelegate()
        setContentView(createContent(savedInstanceState))
        needUpdate = savedInstanceState != null
        delegate.setPresenter(getPresenter())
        delegate.onCreate(this as V, savedInstanceState, intent.extras)
    }

    protected abstract fun getPresenter(): P

    protected abstract fun createContent(savedInstanceState: Bundle?): View

    override fun onStart() {
        super.onStart()
        delegate.onStart()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        delegate.onSavedState(outState)
    }

    override fun onStop() {
        super.onStop()
        delegate.onStop()
    }

    override fun onDestroy() {
        delegate.onDestroy()
        super.onDestroy()
    }

    protected abstract fun inject()

    fun getBaseApplication(): BaseApplication {
        return application as BaseApplication
    }
}