package jp.toneko.decidedartsteams;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by yachi-shunji on 16/01/12.
 */
public class TeamList {
    private static TeamList instance = new TeamList();

    private ArrayList<Team> teamList;

    public static TeamList getInstance() {
        return instance;
    }

    private TeamList() {
        this.teamList = new ArrayList<Team>();
    }

    public void add(Team team) {
        this.teamList.add(team);
    }

    public Team getTeam(int index) {
        return this.teamList.get(index);
    }

    public int getNumOfTeams() {
        return this.teamList.size();
    }

    public void clear() {
        this.teamList.clear();
    }

    public Team getMostRateTeam() {
        int mostTeamIndex = 0;
        int totalRate = getTeam(0).getTotalRate();
        for (int i = 1; i < getNumOfTeams(); i++) {
            int tempRate = getTeam(i).getTotalRate();
            if (tempRate > totalRate) {
                mostTeamIndex = i;
                totalRate = tempRate;
            }
        }
        return getTeam(mostTeamIndex);
    }

    public Team getLeastRateTeam() {
        int leastTeamIndex = 0;
        int totalRate = getTeam(0).getTotalRate();
        for (int i = 1; i < getNumOfTeams(); i++) {
            int tempRate = getTeam(i).getTotalRate();
            if (tempRate < totalRate) {
                leastTeamIndex = i;
                totalRate = tempRate;
            }
        }
        return getTeam(leastTeamIndex);
    }

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

    public Team getOtherTeamOfLeastRateTeam() {
        int leastTeamIndex = getLeastRateTeamIndex();
        ArrayList<Team> otherTeamOfLeastRateTeamIndexList = new ArrayList<Team>();
        for (int i = 0, length = getNumOfTeams(); i < length; i++) {
            if (i != leastTeamIndex) {
                otherTeamOfLeastRateTeamIndexList.add(getTeam(i));
            }
        }
        Random rnd = new Random();
        return otherTeamOfLeastRateTeamIndexList.get(rnd.nextInt(otherTeamOfLeastRateTeamIndexList.size()));
    }

    public Team getLeastRateAveTeam() {
        return getTeam(getLeastRateAveTeamIndex());
    }

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

    public Team getOtherTeamOfLeastRateAveTeam() {
        int leastTeamIndex = getLeastRateAveTeamIndex();
        ArrayList<Team> otherTeamOfLeastRateAveTeamIndexList = new ArrayList<Team>();
        for (int i = 0, length = getNumOfTeams(); i < length; i++) {
            if (i != leastTeamIndex) {
                otherTeamOfLeastRateAveTeamIndexList.add(getTeam(i));
            }
        }
        Random rnd = new Random();
        return otherTeamOfLeastRateAveTeamIndexList.get(rnd.nextInt(otherTeamOfLeastRateAveTeamIndexList.size()));
    }

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
