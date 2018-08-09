package pro.retor.mymvpv2.base.view

interface BaseView {
    fun showError(e: Throwable)

    fun showProgress(show: Boolean)
}