package jp.toneko.decidedartsteams;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.math.BigDecimal;

/**
 * Created by yachi-shunji on 16/01/12.
 */
public class ResultFragment extends Fragment {
    private final int MP = ViewGroup.LayoutParams.MATCH_PARENT;
    private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;

    /**
     * コンストラクタ
     *
     * @return このフラグメントのインスタンス
     */
    public static ResultFragment newInstance() {
        ResultFragment fragment = new ResultFragment();
        return fragment;
    }

    public ResultFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, null);

        TableLayout resultLayout = (TableLayout)view.findViewById(R.id.result_layout);

        // TeamListのインスタンスを取得
        TeamList teamList = TeamList.getInstance();
        for (int i = 0, length = teamList.getNumOfTeams(); i < length; i++) {
            // Teamを取得
            Team team = teamList.getTeam(i);

            // Team情報表示View
            AppCompatTextView teamLabel = new AppCompatTextView(getActivity());
            BigDecimal bd = new BigDecimal(team.getRateAverage()).setScale(1, BigDecimal.ROUND_DOWN);
            teamLabel.setText(" TEAM" + (i + 1) + "    TOTAL RATE:" + team.getTotalRate() + "    RATE AVERAGE:" + bd.toString());
            teamLabel.setBackgroundColor(Color.parseColor("#3F51B5"));
            teamLabel.setTextColor(Color.WHITE);
            resultLayout.addView(teamLabel, new TableLayout.LayoutParams(MP, WC));

            // Team内のMemberを表示する
            for (int j = 0, length2 = team.getNumOfMembers(); j < length2; j++) {
                Member member = team.getMember(j);

                TableRow tableRow = new TableRow(getActivity());

                // 番号表示View
                AppCompatTextView indexView = new AppCompatTextView(getActivity());
                indexView.setText(String.valueOf(j + 1)+":");
                tableRow.addView(indexView, new TableRow.LayoutParams(WC, WC));

                // 名前表示View
                AppCompatTextView memberNameView = new AppCompatTextView(getActivity());
                memberNameView.setText(member.name);
                tableRow.addView(memberNameView, new TableRow.LayoutParams(WC, WC));

                // RATE:表示View
                AppCompatTextView rateTextView = new AppCompatTextView(getActivity());
                rateTextView.setText("RATE:");
                tableRow.addView(rateTextView, new TableRow.LayoutParams(WC, WC));

                // Rate表示View
                AppCompatTextView memberRateView = new AppCompatTextView(getActivity());
                memberRateView.setText(String.valueOf(member.rate));
                tableRow.addView(memberRateView, new TableRow.LayoutParams(WC, WC));

                resultLayout.addView(tableRow, new TableLayout.LayoutParams(MP, WC));
            }
        }

        return view;
    }
}
