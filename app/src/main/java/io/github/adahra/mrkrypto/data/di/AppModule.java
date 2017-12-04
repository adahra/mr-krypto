package io.github.adahra.mrkrypto.data.di;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created on 12/4/17.
 */

@Module
public class AppModule {

    @Provides
    Context provideContext(Application application) {
        return application;
    }
}
