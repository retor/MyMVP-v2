package pro.retor.mymvpv2.application.application

import android.app.Application

abstract class BaseApplication : Application() {
    lateinit var injector: BaseInjector

    override fun onCreate() {
        super.onCreate()
        initInjector()
    }

    protected abstract fun initInjector()

    fun updateInjectorInstance(ars: Map<String, Any>) {

    }
}