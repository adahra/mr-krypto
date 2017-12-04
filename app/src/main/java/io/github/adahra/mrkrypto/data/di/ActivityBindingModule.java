package io.github.adahra.mrkrypto.data.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import io.github.adahra.mrkrypto.presentation.MainActivity;

/**
 * Created on 12/4/17.
 */

@Module
public abstract class ActivityBindingModule {

    @ContributesAndroidInjector
    abstract MainActivity mainActivity();
}
