package io.github.adahra.mrkrypto.presentation;

/**
 * Created on 12/4/17.
 */

public interface BaseView<T extends BasePresenter> {

    void initialize();

    void showPopup(String title, String message);

    void hidePopup();
}
