package jp.toneko.decidedartsteams;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

/**
 * Created by yachi-shunji on 16/01/18.
 */
public class MemberPreferencesUtil {
    // SharedPreferencesファイルキー
    private static final String PREF_KEY = "MemberPreferencesUtil";
    // このクラスのインスタンス
    private static MemberPreferencesUtil OUR_INSTANCE;
    // プリファレンス
    private SharedPreferences pref;
    // Editor
    private SharedPreferences.Editor editor;

    /** Key一覧 **/
    // メンバー数
    private static final String NUM_OF_MEMBERS = "num_of_members";
    // 名前 ("name" + 0, 1, ...)
    private static final String NAME = "name";
    // Rate ("rate" + 0, 1, ...)
    private static final String RATE = "rate";
    // 最後に履歴に登録されたMemberのインデックス
    private static final String LAST_HISTORY_INDEX = "last_history_index";
    // 履歴の名前 ("history_name" + 0, 1, ...)
    private static final String HISTORY_NAME = "history_name";
    // 履歴のRate ("history_rate" + 0, 1, ...)
    private static final String HISTORY_RATE = "history_rate";

    /**
     * コンストラクタ
     * @return このクラスのインスタンス
     */
    public synchronized static MemberPreferencesUtil getInstance(Context context) {
        if (OUR_INSTANCE == null) {
            OUR_INSTANCE = new MemberPreferencesUtil(context);
        }
        return OUR_INSTANCE;
    }

    /**
     * コンストラクタ
     * @param context ApplicationContext
     */
    private MemberPreferencesUtil(Context context) {
        pref = context.getSharedPreferences(PREF_KEY, Activity.MODE_PRIVATE);
        editor = pref.edit();
    }

    /**
     * メンバーリスト読み込み
     * @return 設定値
     */
    public void loadMemberList() {
        MemberList memberList = MemberList.getInstance();
        memberList.clear();
        int numOfMembers = pref.getInt(NUM_OF_MEMBERS, 0);
        for (int i = 0; i < numOfMembers; i++) {
            String name = pref.getString(NAME + i, "");
            int rate = pref.getInt(RATE + i, 0);
            memberList.add(new Member(name, rate));
        }
    }

    /**
     * メンバーリスト保存
     */
    public void saveMemberList() {
        MemberList memberList = MemberList.getInstance();
        int numOfMembers = memberList.getNumOfMembers();
        editor.putInt(NUM_OF_MEMBERS, numOfMembers);
        for (int i = 0; i < numOfMembers; i++) {
            Member member = memberList.getMember(i);
            editor.putString(NAME + i, member.name);
            editor.putInt(RATE + i, member.rate);
            addMemberToHistory(member);
        }
        editor.commit();
    }

    /**
     * 履歴にMemberを追加する
     * @param member
     */
    public void addMemberToHistory(Member member) {
        int lastHistoryIndex = pref.getInt(LAST_HISTORY_INDEX, 0);
        editor.putString(HISTORY_NAME + String.valueOf(lastHistoryIndex + 1), member.name);
        editor.putInt(HISTORY_RATE + (lastHistoryIndex + 1), member.rate);
        editor.putInt(LAST_HISTORY_INDEX, lastHistoryIndex + 1);
        editor.commit();
    }

    /**
     * MemberのArrayを履歴件数分取得する
     * @param historyNumber 履歴件数
     * @return ArrayList<Member>
     */
    public ArrayList<Member> getMembersFromHistory(int historyNumber) {
        ArrayList<Member> members = new ArrayList<Member>();

        int lastHistoryIndex = pref.getInt(LAST_HISTORY_INDEX, 0);
        for (int i = 0; i < historyNumber; i++) {
            String name = pref.getString(HISTORY_NAME + ((lastHistoryIndex - (historyNumber - 1)) + i), "");
            int rate = pref.getInt(HISTORY_RATE + ((lastHistoryIndex - (historyNumber - 1)) + i), 0);
            if (!"".equals(name) || rate != 0) {
                members.add(new Member(name, rate));
            }
        }
        return members;
    }

    /**
     * 初期化
     */
    public void clearUri() {
        editor.clear().commit();
    }
}
