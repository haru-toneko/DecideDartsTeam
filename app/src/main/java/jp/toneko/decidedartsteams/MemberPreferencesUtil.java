package jp.toneko.decidedartsteams;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by yachi-shunji on 16/01/18.
 */
public class MemberPreferencesUtil {
    /** SharedPreferencesファイルキー */
    private static final String PREF_KEY = "MemberPreferencesUtil";
    /** このクラスのインスタンス */
    private static MemberPreferencesUtil OUR_INSTANCE;
    /** プリファレンス */
    private SharedPreferences pref;
    /** Editor */
    private SharedPreferences.Editor editor;

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
        int numOfMembers = pref.getInt("num_of_members", 0);
        for (int i = 0; i < numOfMembers; i++) {
            String name = pref.getString("name" + i, "");
            int rate = pref.getInt("rate" + i, 0);
            memberList.add(new Member(name, rate));
        }
    }

    /**
     * メンバーリスト保存
     */
    public void saveMemberList() {
        MemberList memberList = MemberList.getInstance();
        int numOfMembers = memberList.getNumOfMembers();
        editor.putInt("num_of_members", numOfMembers);
        for (int i = 0; i < numOfMembers; i++) {
            Member member = memberList.getMember(i);
            editor.putString("name" + i, member.name);
            editor.putInt("rate" + i, member.rate);
            addMemberToHistory(member);
        }
        editor.commit();
    }

    public void addMemberToHistory(Member member) {
        int lastHistoryIndex = pref.getInt("last_history_index", 0);
        editor.putString("history_name" + String.valueOf(lastHistoryIndex + 1), member.name);
        editor.putInt("history_rate" + (lastHistoryIndex + 1), member.rate);
        editor.putInt("last_history_index", lastHistoryIndex + 1);
        editor.commit();
    }

    public Member getMemberFromHistory(int index) {
        int lastHistoryIndex = pref.getInt("last_history_index", 0);
        String name = pref.getString("history_name" + ((lastHistoryIndex - 29) + index), "");
        int rate = pref.getInt("history_rate" + ((lastHistoryIndex - 29) + index), 0);
        if ("".equals(name) && rate == 0) {
            return null;
        }
        return new Member(name, rate);
    }

    /**
     * 初期化
     */
    public void clearUri() {
        editor.clear().commit();
    }
}
