package com.vebovs.tetris;

import android.os.Bundle;
import android.preference.PreferenceFragment;

public class Preference extends PreferenceFragment {

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
