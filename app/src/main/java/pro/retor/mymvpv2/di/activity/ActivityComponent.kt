package pro.retor.mymvpv2.di.activity

interface ActivityComponent<T> {
    fun inject(activity: T)
}