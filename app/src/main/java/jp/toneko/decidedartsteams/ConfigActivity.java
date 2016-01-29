package jp.toneko.decidedartsteams;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by yachi-shunji on 16/01/13.
 * 設定画面、現在使われていない
 */
public class ConfigActivity extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ConfigFragmentをcontentに組み込む
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new ConfigFragment()).commit();


    }
}
