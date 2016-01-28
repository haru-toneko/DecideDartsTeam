package jp.toneko.decidedartsteams;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by yachi-shunji on 16/01/12.
 */
public class Team {
    private ArrayList<Member> team;

    public Team() {
        this.team = new ArrayList<Member>();
    }

    public void add(Member member) {
        this.team.add(member);
    }

    public Member getMember(int index) {
        return this.team.get(index);
    }

    public int getNumOfMembers() {
        return this.team.size();
    }

    public int getTotalRate() {
        int totalRate = 0;
        for (int i = 0, length = getNumOfMembers(); i < length; i++) {
            totalRate += getMember(i).rate;
        }
        return totalRate;
    }

    public float getRateAverage() {
        return ((float)getTotalRate() / (float)getNumOfMembers());
    }

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
