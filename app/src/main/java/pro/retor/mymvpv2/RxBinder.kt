package pro.retor.mymvpv2

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function

object RxBinder {

    fun <T> ui(onNext: Consumer<T>, onError: Consumer<Throwable>): Function<Observable<T>, Disposable> {
        return Function {
            it.observeOn(AndroidSchedulers.mainThread()).subscribe(onNext, onError)
        }
    }

    fun <T> uiFlowable(onNext: Consumer<T>, onError: Consumer<Throwable>): Function<Flowable<T>, Disposable> {
        return Function {
            it.observeOn(AndroidSchedulers.mainThread()).subscribe(onNext, onError)
        }
    }

    fun <T> uiMaybe(onNext: Consumer<T>, onError: Consumer<Throwable>): Function<Maybe<T>, Disposable> {
        return Function {
            it.observeOn(AndroidSchedulers.mainThread()).subscribe(onNext, onError)
        }
    }

    fun <T> uiSingle(onNext: Consumer<T>, onError: Consumer<Throwable>): Function<Single<T>, Disposable> {
        return Function {
            it.observeOn(AndroidSchedulers.mainThread()).subscribe(onNext, onError)
        }
    }

    fun <T> uiCompletable(onComplete: Action, onError: Consumer<Throwable>): Function<Completable, Disposable> {
        return Function {
            it.observeOn(AndroidSchedulers.mainThread()).subscribe(onComplete, onError)
        }
    }

    fun <T> bind(from: Observable<T>, uiFunction: Function<Observable<T>, Disposable>): Disposable {
        return uiFunction.apply(from)
    }

    fun <T> bind(from: Flowable<T>, uiFunction: Function<Flowable<T>, Disposable>): Disposable {
        return uiFunction.apply(from)
    }

    fun <T> bind(from: Maybe<T>, uiFunction: Function<Maybe<T>, Disposable>): Disposable {
        return uiFunction.apply(from)
    }

    fun <T> bind(from: Single<T>, uiFunction: Function<Single<T>, Disposable>): Disposable {
        return uiFunction.apply(from)
    }

    fun <T> bind(from: Completable, uiFunction: Function<Completable, Disposable>): Disposable {
        return uiFunction.apply(from)
    }
}