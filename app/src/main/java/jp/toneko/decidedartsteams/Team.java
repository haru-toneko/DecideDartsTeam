package jp.toneko.decidedartsteams;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by yachi-shunji on 16/01/12.
 */
public class Team {
    private ArrayList<Member> team;

    /**
     * コンストラクタ
     */
    public Team() {
        this.team = new ArrayList<Member>();
    }

    /**
     * Memberを追加
     * @param member
     */
    public void add(Member member) {
        this.team.add(member);
    }

    /**
     * Memberを取得
     * @param index
     * @return
     */
    public Member getMember(int index) {
        return this.team.get(index);
    }

    /**
     * メンバー数を取得
     * @return
     */
    public int getNumOfMembers() {
        return this.team.size();
    }

    /**
     * Team内のメンバーのRateの合計値を取得
     * @return
     */
    public int getTotalRate() {
        int totalRate = 0;
        for (int i = 0, length = getNumOfMembers(); i < length; i++) {
            totalRate += getMember(i).rate;
        }
        return totalRate;
    }

    /**
     * Team内のメンバーのRateの平均値を取得
     * @return
     */
    public float getRateAverage() {
        return ((float)getTotalRate() / (float)getNumOfMembers());
    }

    /**
     * Memberを削除
     * @param index
     */
    public void removeMember(int index) {
        this.team.remove(index);
    }

    /**
     * 入力レートより高いレートのメンバーをランダムで返す
     * @param rate (入力レートより高いレートのメンバーがいない場合、ランダムでメンバーのインデックスを返す)
     */
    public int getHigherRateMember(int rate) {
        ArrayList<Integer> higherRateMemberIndexList = new ArrayList<Integer>();
        for (int i = 0, length = getNumOfMembers(); i < length; i++) {
            Member tempMember = getMember(i);
            if (tempMember.rate > rate) {
                higherRateMemberIndexList.add(i);
            }
        }
        Random rnd = new Random();
        if (higherRateMemberIndexList.isEmpty()) {
            return rnd.nextInt(this.team.size());
        }
        return higherRateMemberIndexList.get(rnd.nextInt(higherRateMemberIndexList.size()));
    }
}
