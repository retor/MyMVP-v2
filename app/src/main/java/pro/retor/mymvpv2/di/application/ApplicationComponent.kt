package pro.retor.mymvpv2.di.application

import android.content.Context
import pro.retor.mymvpv2.application.application.BaseApplication

interface ApplicationComponent {
    fun providesContext(): Context

    fun providesApplication(): BaseApplication
}