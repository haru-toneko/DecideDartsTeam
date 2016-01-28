package jp.toneko.decidedartsteams;

import android.app.Activity;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by yachi-shunji on 16/01/12.
 */
public class InputFragment extends Fragment implements View.OnClickListener {
    private OnFragmentInteractionListener mListener;

    private final int MP = ViewGroup.LayoutParams.MATCH_PARENT;
    private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;

    private int numOfTeams;
    private int numOfMembers;

    /**
     * コンストラクタ
     *
     * @return このフラグメントのインスタンス
     */
    public static InputFragment newInstance() {
        InputFragment fragment = new InputFragment();
        return fragment;
    }

    public InputFragment() {
    }

    /**
     * onAttach
     *
     * @param activity activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input, null);

        Button settingTeamButton = (Button) view.findViewById(R.id.setting_button);
        settingTeamButton.setOnClickListener(this);
        Button resultButton = (Button) view.findViewById(R.id.result_button);
        resultButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_button:
                if (!isInputOK()) {
                    return;
                }
                loadMemberInfo();
                updateMemberList();
                saveInput();
                clearView();
                settingMemberViews();
                settingResultButton();
                break;
            case R.id.result_button:
                if (!isInputAllData()) {
                    return;
                }
                updateMemberList();
                saveMemberListToPreferences();
                if (!decideTeams()) {
                    return;
                }
                if (mListener != null) {
                    mListener.onFragmentInteraction(view.getId());
                }
                break;
            default:
                break;
        }
    }

    private void clearView() {
        TableLayout settingLayout = (TableLayout)getActivity().findViewById(R.id.setting_layout);
        settingLayout.removeAllViews();
    }

    private void loadMemberInfo() {
        MemberPreferencesUtil.getInstance(getActivity()).loadMemberList();
    }

    private void settingMemberViews() {
        TableLayout settingLayout = (TableLayout)getActivity().findViewById(R.id.setting_layout);
        settingLayout.setColumnStretchable(4, true);

        int id = 0;
        MemberList memberList = MemberList.getInstance();
        for (int i = 0; i < this.numOfMembers; i++) {

            TableRow tableRow = new TableRow(getActivity());
            Member member = memberList.getMember(i);

            AppCompatTextView indexView = new AppCompatTextView(getActivity());
            indexView.setText(String.valueOf(i + 1) + ":");
            tableRow.addView(indexView, new TableRow.LayoutParams(WC, WC));

            AppCompatEditText ed1 = new AppCompatEditText(getActivity());
            ed1.setEms(6);
            ed1.setId(id++);
            ed1.setHint("NAME");
            if (member != null) {
                ed1.setText(member.name);
            }
            tableRow.addView(ed1, new TableRow.LayoutParams(WC, WC));

            AppCompatTextView tv = new AppCompatTextView(getActivity());
            tv.setText("RATE:");
            tableRow.addView(tv, new TableRow.LayoutParams(WC, WC));

            AppCompatEditText ed2 = new AppCompatEditText(getActivity());
            ed2.setEms(2);
            InputFilter[] _inputFilter = new InputFilter[1];
            _inputFilter[0] = new InputFilter.LengthFilter(2);
            ed2.setFilters(_inputFilter);
            ed2.setInputType(InputType.TYPE_CLASS_NUMBER);
            ed2.setId(id++);
            if (member != null) {
                ed2.setText(String.valueOf(member.rate));
            }
            tableRow.addView(ed2, new TableRow.LayoutParams(WC, WC));

            LinearLayout ll = new LinearLayout(getActivity());
            ll.setGravity(Gravity.RIGHT);
            AppCompatButton bt = new AppCompatButton(getActivity());
            bt.setText("履歴");
            bt.setId(-i);
            bt.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    final int textViewId = -(v.getId() * 2);

                    MemberPreferencesUtil memberPreferencesUtil = MemberPreferencesUtil.getInstance(getActivity());
                    final ArrayList<Member> members = new ArrayList<Member>();
                    for (int i = 0; i < 30; i++) {
                        Member member = memberPreferencesUtil.getMemberFromHistory(i);
                        if (member != null) {
                            Log.d("name"+i, member.name);
                            members.add(member);
                        }
                    }
                    CharSequence[] items = new CharSequence[members.size()];
                    for (int i = 0, length = members.size(); i < length; i++) {
                        Member member = members.get(i);
                        items[i] = member.name + " RATE:" + String.valueOf(member.rate);
                    }
                    AlertDialog.Builder listDialog = new AlertDialog.Builder(getActivity());
                    listDialog.setTitle("履歴");
                    listDialog.setItems(
                            items,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    EditText name = (EditText)getActivity().findViewById(textViewId);
                                    name.setText(members.get(which).name);
                                    EditText rate = (EditText)getActivity().findViewById(textViewId + 1);
                                    rate.setText(String.valueOf(members.get(which).rate));
                                }
                            });
                    listDialog.create().show();
                }
            });
            ll.addView(bt, new LinearLayout.LayoutParams(WC, WC));
            tableRow.addView(ll, new TableRow.LayoutParams(MP, WC));

            settingLayout.addView(tableRow, new TableLayout.LayoutParams(MP, WC));
        }
    }

    private void saveInput() {
        this.numOfTeams = Integer.parseInt(((EditText)getActivity().findViewById(R.id.num_of_teams)).getText().toString());
        this.numOfMembers = Integer.parseInt(((EditText) getActivity().findViewById(R.id.num_of_members)).getText().toString());
    }

    private void settingResultButton() {
        Button resultButton = (Button)getActivity().findViewById(R.id.result_button);
        resultButton.setVisibility(View.VISIBLE);
    }

    private void updateMemberList() {
        int numOfMembers = ((TableLayout)getActivity().findViewById(R.id.setting_layout)).getChildCount();
        if (numOfMembers <= 0) {
            return;
        }

        MemberList memberList = MemberList.getInstance();
        memberList.clear();

        for (int i = 0; i < numOfMembers; i++) {
            try {
                String name = ((EditText)getActivity().findViewById(i * 2)).getText().toString();
                int rate = Integer.parseInt(((EditText) getActivity().findViewById(i * 2 + 1)).getText().toString());
                memberList.add(new Member(name, rate));
            }catch (Exception e) {
            }
        }
    }

    private void saveMemberListToPreferences() {
        MemberPreferencesUtil.getInstance(getActivity()).saveMemberList();
    }

    /**
     * チームを決める
     * @return true: 成功, false: 失敗
     */
    private boolean decideTeams() {
        TeamList teamList = TeamList.getInstance();
        // teamListをリセット
        teamList.clear();
        // チーム数分のチームを作成
        for (int i = 0; i < this.numOfTeams; i++) {
            teamList.add(new Team());
        }

        MemberList memberList = MemberList.getInstance();
        // memberListをシャッフル
        memberList.shuffle();
        // 順番にチームに入れる
        int teamIndex = 0;
        for (int i = 0; i < this.numOfMembers; i++) {
            teamList.getTeam(teamIndex).add(memberList.getMember(i));
            teamIndex++;
            if (teamIndex >= numOfTeams) {
                teamIndex = 0;
            }
        }

        Random rnd = new Random();
        float maxRateAveDifference = Float.parseFloat(((EditText) getActivity().findViewById(R.id.allowable_rate_ave_difference)).getText().toString());
        for (int i = 0; ; i++) {
            // 最もRate平均の低いチームとその他のチームを１つ取得
            Team leastRateTeam = teamList.getLeastRateAveTeam();
            Team otherTeam = teamList.getOtherTeamOfLeastRateAveTeam();

            // チーム間のRate平均差が設定した数値以下か、100回入れ替えても駄目ならループを抜ける
            float maxRateAve = teamList.getMostRateAveTeam().getRateAverage();
            float leastRateAve = leastRateTeam.getRateAverage();
            Log.d("maxRateAve", ""+maxRateAve);
            Log.d("leastRateAve", ""+maxRateAve);
            if ((maxRateAve - leastRateAve) <= maxRateAveDifference) {
                break;
            }
            if (i >= 100) {
                Toast.makeText(getActivity(), "チーム分けに失敗しました。\n許容レート平均差を変更してください。", Toast.LENGTH_SHORT).show();
                return false;
            }

            // Rate平均の低いチームのメンバーのインデックス (ランダム)
            int otherTeamMemberIndex = rnd.nextInt(otherTeam.getNumOfMembers());
            int leastRateTeamMemberIndex = rnd.nextInt(leastRateTeam.getNumOfMembers());
            // メンバー入れ替え
            Member mostRateTeamMember = otherTeam.getMember(otherTeamMemberIndex);
            Member leastRateTeamMember = leastRateTeam.getMember(leastRateTeamMemberIndex);
            otherTeam.removeMember(otherTeamMemberIndex);
            leastRateTeam.removeMember(leastRateTeamMemberIndex);
            otherTeam.add(leastRateTeamMember);
            leastRateTeam.add(mostRateTeamMember);
        }
        return true;
    }

    private boolean isInputOK() {
        try {
            int numOfTeams = Integer.parseInt(((EditText)getActivity().findViewById(R.id.num_of_teams)).getText().toString());
            int numOfMembers = Integer.parseInt(((EditText) getActivity().findViewById(R.id.num_of_members)).getText().toString());
            float maxRateAveDifference = Float.parseFloat(((EditText) getActivity().findViewById(R.id.allowable_rate_ave_difference)).getText().toString());
            if (numOfTeams <= 0 || numOfMembers <= 0 || maxRateAveDifference < 0 || numOfTeams > numOfMembers) {
                Toast.makeText(getActivity(), "入力された数値が正しくありません", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "数値が入力されていません", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean isInputAllData() {
        try {
            int numOfMembers = Integer.parseInt(((EditText)getActivity().findViewById(R.id.num_of_members)).getText().toString());
            for (int i = 0, length = numOfMembers; i < length; i++) {
                String name = ((EditText)getActivity().findViewById(i * 2)).getText().toString();
                int rate = Integer.parseInt(((EditText) getActivity().findViewById(i * 2 + 1)).getText().toString());
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "人数分入力されていません", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
