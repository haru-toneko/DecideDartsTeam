package jp.toneko.decidedartsteams;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by yachi-shunji on 16/01/13.
 */
public class MemberList {
    private static MemberList instance = new MemberList();

    private ArrayList<Member> memberList;

    /**
     * MemberListのインスタンスを取得 (シングルトン)
     * @return
     */
    public static MemberList getInstance() {
        return instance;
    }

    /**
     * コンストラクタ
     */
    private MemberList(){
        this.memberList = new ArrayList<Member>();
    }

    /**
     * MemberList内の配列を取得する
     * @return
     */
    public ArrayList<Member> getMemberList() {
        return this.memberList;
    }

    /**
     * Memberを追加
     * @param member
     */
    public void add(Member member) {
        this.memberList.add(member);
    }

    /**
     * メンバー数を取得
     * @return
     */
    public int getNumOfMembers() {
        return this.memberList.size();
    }

    /**
     * MemberListを初期化
     */
    public void clear() {
        this.memberList.clear();
    }

    /**
     * Memberを取得
     * @param index
     * @return
     */
    public Member getMember(int index) {
        try {
            return this.memberList.get(index);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    /**
     * Memberの順番をシャッフルする
     */
    public void shuffle() {
        Collections.shuffle(this.memberList);
    }
}
