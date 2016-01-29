package jp.toneko.decidedartsteams;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by yachi-shunji on 16/01/12.
 */
public class TeamList {
    private static TeamList instance = new TeamList();

    private ArrayList<Team> teamList;

    /**
     * TeamListのインスタンスを取得 (シングルトン)
     * @return
     */
    public static TeamList getInstance() {
        return instance;
    }

    /**
     * コンストラクタ
     */
    private TeamList() {
        this.teamList = new ArrayList<Team>();
    }

    /**
     * Teamを追加
     * @param team
     */
    public void add(Team team) {
        this.teamList.add(team);
    }

    /**
     * Teamを取得
     * @param index
     * @return
     */
    public Team getTeam(int index) {
        return this.teamList.get(index);
    }

    /**
     * チーム数を取得
     * @return
     */
    public int getNumOfTeams() {
        return this.teamList.size();
    }

    /**
     * TeamListを初期化
     */
    public void clear() {
        this.teamList.clear();
    }

    /**
     * 最もRate合計が高いTeamを取得
     * @return
     */
    public Team getMostRateTeam() {
        return getTeam(getMostRateTeamIndex());
    }

    /**
     * 最もRate合計が高いTeamのインデックスを取得
     * @return
     */
    public int getMostRateTeamIndex() {
        int mostTeamIndex = 0;
        int totalRate = getTeam(0).getTotalRate();
        for (int i = 1; i < getNumOfTeams(); i++) {
            int tempRate = getTeam(i).getTotalRate();
            if (tempRate > totalRate) {
                mostTeamIndex = i;
                totalRate = tempRate;
            }
        }
        return mostTeamIndex;
    }

    /**
     * 最もRate合計が低いTeamを取得
     * @return
     */
    public Team getLeastRateTeam() {
        return getTeam(getLeastRateTeamIndex());
    }

    /**
     * 最もRate合計が低いTeamのインデックスを取得
     * @return
     */
    public int getLeastRateTeamIndex() {
        int leastTeamIndex = 0;
        int totalRate = getTeam(0).getTotalRate();
        for (int i = 1; i < getNumOfTeams(); i++) {
            int tempRate = getTeam(i).getTotalRate();
            if (tempRate < totalRate) {
                leastTeamIndex = i;
                totalRate = tempRate;
            }
        }
        return leastTeamIndex;
    }

    /**
     * 最もRate合計が低いチームの他のチームを取得 (ランダム)
     * @return
     */
    public Team getAnotherTeamOfLeastRateTeam() {
        int leastTeamIndex = getLeastRateTeamIndex();
        ArrayList<Team> anotherTeamOfLeastRateTeamIndexList = new ArrayList<Team>();
        for (int i = 0, length = getNumOfTeams(); i < length; i++) {
            if (i != leastTeamIndex) {
                anotherTeamOfLeastRateTeamIndexList.add(getTeam(i));
            }
        }
        Random rnd = new Random();
        return anotherTeamOfLeastRateTeamIndexList.get(rnd.nextInt(anotherTeamOfLeastRateTeamIndexList.size()));
    }

    /**
     * 最もRate平均が低いチームを取得
     * @return
     */
    public Team getLeastRateAveTeam() {
        return getTeam(getLeastRateAveTeamIndex());
    }

    /**
     * 最もRate平均が低いチームのインデックスを取得
     * @return
     */
    public int getLeastRateAveTeamIndex() {
        int leastTeamIndex = 0;
        float rateAverage = getTeam(0).getRateAverage();
        for (int i = 1; i < getNumOfTeams(); i++) {
            float tempRateAve = getTeam(i).getRateAverage();
            if (tempRateAve < rateAverage) {
                leastTeamIndex = i;
                rateAverage = tempRateAve;
            }
        }
        return leastTeamIndex;
    }

    /**
     * 最もRate平均が低いチームの他のチームを取得 (ランダム)
     * @return
     */
    public Team getAnotherTeamOfLeastRateAveTeam() {
        int leastTeamIndex = getLeastRateAveTeamIndex();
        ArrayList<Team> anotherTeamOfLeastRateAveTeamIndexList = new ArrayList<Team>();
        for (int i = 0, length = getNumOfTeams(); i < length; i++) {
            if (i != leastTeamIndex) {
                anotherTeamOfLeastRateAveTeamIndexList.add(getTeam(i));
            }
        }
        Random rnd = new Random();
        return anotherTeamOfLeastRateAveTeamIndexList.get(rnd.nextInt(anotherTeamOfLeastRateAveTeamIndexList.size()));
    }

    /**
     * 最もRate平均が高いチームを取得
     * @return
     */
    public Team getMostRateAveTeam() {
        int mostTeamIndex = 0;
        float rateAve = getTeam(0).getRateAverage();
        for (int i = 1; i < getNumOfTeams(); i++) {
            float tempRate = getTeam(i).getRateAverage();
            if (tempRate > rateAve) {
                mostTeamIndex = i;
                rateAve = tempRate;
            }
        }
        return getTeam(mostTeamIndex);
    }

}
