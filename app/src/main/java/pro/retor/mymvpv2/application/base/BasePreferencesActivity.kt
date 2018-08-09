package pro.retor.mymvpv2.application.base

import io.reactivex.functions.Consumer
import pro.retor.mymvpv2.application.internal.InternalPreferenceActivity
import pro.retor.mymvpv2.base.model.BaseModel
import pro.retor.mymvpv2.base.presenter.Presenter
import pro.retor.mymvpv2.base.view.BaseView
import pro.retor.mymvpv2.di.activity.ActivityComponent
import javax.inject.Inject

/**
 * Created by retor on 11.07.2016.
 */
abstract class BasePreferencesActivity<M : BaseModel, V : BaseView, P : Presenter<M, V>> : InternalPreferenceActivity<M, V, P>() {


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
