package io.github.adahra.mrkrypto.data.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.support.AndroidSupportInjectionModule;
import io.github.adahra.mrkrypto.MrKryptoApp;

/**
 * Created on 12/4/17.
 */

@Singleton
@Component(modules = {
        AppModule.class, ActivityBindingModule.class,
        FragmentBindingModule.class, AndroidSupportInjectionModule.class
})
public interface AppComponent extends AndroidInjector<DaggerApplication> {

    void inject(MrKryptoApp mrKryptoApp);

    @Component.Builder
    interface Builder {

        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }
}
