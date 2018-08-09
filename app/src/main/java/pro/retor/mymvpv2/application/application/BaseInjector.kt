package pro.retor.mymvpv2.application.application

import pro.retor.mymvpv2.di.application.ApplicationComponent

abstract class BaseInjector(private val application: BaseApplication) {

    abstract fun providesAppComponent(): ApplicationComponent

}