package io.github.adahra.mrkrypto.presentation;

import android.os.Bundle;

import butterknife.ButterKnife;
import io.github.adahra.mrkrypto.R;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
}
