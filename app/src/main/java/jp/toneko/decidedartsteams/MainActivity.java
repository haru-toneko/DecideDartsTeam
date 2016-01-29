package jp.toneko.decidedartsteams;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // InputFragmentをactivity_mainのレイアウトに組み込む
        fragmentTransaction = getFragmentManager().beginTransaction();
        Fragment fragment = InputFragment.newInstance();
        fragmentTransaction.replace(R.id.container, fragment, FragmentTags.INPUT.getName());
        fragmentTransaction.commit();
    }

    /**
     * Fragment内のボタンを押したときのコールバック処理
     * @param buttonId
     */
    @Override
    public void onFragmentInteraction(int buttonId) {
        if (buttonId == R.id.result_button) {
            // ResultFragmentをactivity_mainのレイアウトに組み込む
            fragmentTransaction = getFragmentManager().beginTransaction();
            Fragment fragment = ResultFragment.newInstance();
            fragmentTransaction.replace(R.id.container, fragment, FragmentTags.RESULT.getName());
            // バックスタックに追加
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    /**
     * Android端末の戻るボタンを押した時のイベント処理
     */
    @Override
    public void onBackPressed() {
        // バックスタックにFragmentがあるとき一つ戻る
        int backStackCnt = getFragmentManager().getBackStackEntryCount();
        if (backStackCnt > 0) {
            fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.detach(getFragmentManager().findFragmentByTag(getVisibleFragmentTag()));
            fragmentTransaction.commit();
            getFragmentManager().popBackStack();
        } else {
            // バックスタックにFragmentがないときアプリ終了
            finish();
        }
    }

    /**
     * 現在表示されているFragmentのタグ名を取得する
     * @return タグ名
     */
    private String getVisibleFragmentTag() {
        if (getFragmentManager().findFragmentByTag(FragmentTags.INPUT.getName()) != null &&
                getFragmentManager().findFragmentByTag(FragmentTags.INPUT.getName()).isVisible()) {
            return FragmentTags.INPUT.getName();
        } else if (getFragmentManager().findFragmentByTag(FragmentTags.RESULT.getName()) != null &&
                getFragmentManager().findFragmentByTag(FragmentTags.RESULT.getName()).isVisible()) {
            return FragmentTags.RESULT.getName();
        } else {
            return FragmentTags.INPUT.getName();
        }
    }
}
