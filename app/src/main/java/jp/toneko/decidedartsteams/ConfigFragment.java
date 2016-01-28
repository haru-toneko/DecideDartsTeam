package jp.toneko.decidedartsteams;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by yachi-shunji on 16/01/13.
 */
public class ConfigFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);
    }
}
