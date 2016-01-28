package jp.toneko.decidedartsteams;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentTransaction = getFragmentManager().beginTransaction();
        Fragment fragment = InputFragment.newInstance();
        fragmentTransaction.replace(R.id.container, fragment, FragmentTags.INPUT.getName());
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentInteraction(int buttonId) {
        if (buttonId == R.id.result_button) {
            fragmentTransaction = getFragmentManager().beginTransaction();
            Fragment fragment = ResultFragment.newInstance();
            fragmentTransaction.replace(R.id.container, fragment, FragmentTags.RESULT.getName());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onBackPressed() {
        int backStackCnt = getFragmentManager().getBackStackEntryCount();
        if (backStackCnt > 0) {
            fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.detach(getFragmentManager().findFragmentByTag(getVisibleFragmentTag()));
            fragmentTransaction.commit();
            getFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

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
