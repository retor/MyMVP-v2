package pro.retor.mymvpv2.di.fragment

interface FragmentComponent<T> {
    fun inject(fragment: T)
}