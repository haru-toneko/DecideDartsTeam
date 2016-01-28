package jp.toneko.decidedartsteams;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by yachi-shunji on 16/01/13.
 */
public class ConfigActivity extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new ConfigFragment()).commit();


    }
}
