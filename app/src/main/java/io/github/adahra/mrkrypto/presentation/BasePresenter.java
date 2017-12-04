package io.github.adahra.mrkrypto.presentation;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created on 12/4/17.
 */

public class BasePresenter<V extends BaseView> {
    protected V view;
    private CompositeDisposable compositeDisposable;

    public void attach(V view) {
        this.view = view;
        if (this.compositeDisposable == null) {
            this.compositeDisposable = new CompositeDisposable();
        }
    }

    public void detach() {
        this.view = null;
        stopTask();
    }

    public void addTask(Disposable disposable) {
        this.compositeDisposable.add(disposable);
    }

    public void stopTask() {
        if (this.compositeDisposable != null && !this.compositeDisposable.isDisposed()) {
            this.compositeDisposable.clear();
        }
    }
}
