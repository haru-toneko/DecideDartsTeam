package jp.toneko.decidedartsteams;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by yachi-shunji on 16/01/13.
 */
public class MemberList {
    private static MemberList instance = new MemberList();

    private ArrayList<Member> memberList;

    public static MemberList getInstance() {
        return instance;
    }

    private MemberList(){
        this.memberList = new ArrayList<Member>();
    }

    public ArrayList<Member> getMemberList() {
        return this.memberList;
    }

    public void add(Member member) {
        this.memberList.add(member);
    }

    public int getNumOfMembers() {
        return this.memberList.size();
    }

    public void clear() {
        this.memberList.clear();
    }

    public Member getMember(int index) {
        try {
            return this.memberList.get(index);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public void shuffle() {
        Collections.shuffle(this.memberList);
    }
}
