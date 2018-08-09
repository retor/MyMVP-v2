package pro.retor.mymvpv2.application.base

import io.reactivex.functions.Consumer
import pro.retor.mymvpv2.application.internal.InternalActivity
import pro.retor.mymvpv2.base.model.BaseModel
import pro.retor.mymvpv2.base.presenter.Presenter
import pro.retor.mymvpv2.base.view.BaseView
import pro.retor.mymvpv2.di.activity.ActivityComponent
import javax.inject.Inject

abstract class BaseActivity<M : BaseModel, V : BaseView, P : Presenter<M, V>> : InternalActivity<M, V, P>() {

    @Inject
    protected lateinit var pres: P

    abstract fun getComponent(): ActivityComponent<V>

    override fun getPresenter(): P {
        return pres
    }

    fun showError(): Consumer<Throwable> {
        return Consumer {
            showError(it)
        }
    }
}