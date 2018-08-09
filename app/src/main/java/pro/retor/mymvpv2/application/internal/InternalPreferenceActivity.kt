package pro.retor.mymvpv2.application.internal

import android.content.res.Configuration
import android.os.Bundle
import android.preference.PreferenceActivity
import android.support.annotation.LayoutRes
import android.support.annotation.NonNull
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatDelegate
import android.support.v7.widget.Toolbar
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import pro.retor.mymvpv2.base.model.BaseModel
import pro.retor.mymvpv2.base.presenter.Presenter
import pro.retor.mymvpv2.base.view.BaseView


abstract class InternalPreferenceActivity<M : BaseModel, V : BaseView, P : Presenter<M, V>> : PreferenceActivity(), BaseView {

    private var mDelegate: AppCompatDelegate? = null

    @NonNull
    private lateinit var delegate: LifeCicleDelegate<M, V, P>
    private var needUpdate: Boolean = false

    val supportActionBar: ActionBar?
        get() = getDelegate().supportActionBar

    protected abstract fun getPresenter(): P

    override fun onCreate(savedInstanceState: Bundle?) {
        getDelegate().installViewFactory()
        getDelegate().onCreate(savedInstanceState)
        super.onCreate(savedInstanceState)
        inject()
        delegate = LifeCicleDelegate()
        needUpdate = savedInstanceState == null
        delegate.setPresenter(getPresenter())
        delegate.onCreate(this as V, savedInstanceState, intent.extras)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        getDelegate().onPostCreate(savedInstanceState)
    }

    fun setSupportActionBar(toolbar: Toolbar?) {
        getDelegate().setSupportActionBar(toolbar)
    }

    override fun getMenuInflater(): MenuInflater {
        return getDelegate().menuInflater
    }

    override fun setContentView(@LayoutRes layoutResID: Int) {
        getDelegate().setContentView(layoutResID)
    }

    override fun setContentView(view: View) {
        getDelegate().setContentView(view)
    }

    override fun setContentView(view: View, params: ViewGroup.LayoutParams) {
        getDelegate().setContentView(view, params)
    }

    override fun addContentView(view: View, params: ViewGroup.LayoutParams) {
        getDelegate().addContentView(view, params)
    }

    override fun onPostResume() {
        super.onPostResume()
        getDelegate().onPostResume()
    }

    override fun onTitleChanged(title: CharSequence, color: Int) {
        super.onTitleChanged(title, color)
        getDelegate().setTitle(title)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        getDelegate().onConfigurationChanged(newConfig)
    }

    override fun onStop() {
        super.onStop()
        getDelegate().onStop()
        delegate.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        getDelegate().onDestroy()
        delegate.onDestroy()
    }

    override fun invalidateOptionsMenu() {
        getDelegate().invalidateOptionsMenu()
    }

    private fun getDelegate(): AppCompatDelegate {
        if (mDelegate == null) {
            mDelegate = AppCompatDelegate.create(this, null)
        }
        return mDelegate!!
    }

    protected abstract fun inject()

    protected abstract fun createContent(savedInstanceState: Bundle): View

    override fun onStart() {
        super.onStart()
        delegate.onStart()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        delegate.onSavedState(outState)
    }
}
