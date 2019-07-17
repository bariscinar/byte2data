package net.byte2data.score.db.pojo;

import java.util.Date;

/**
 * Created by barisci on 15.11.2016.
 */
public class League {

    private int id;
    private int leagueId;
    private int countryId;
    private String leagueName;
    private short fixture;
    private short isCup;
    private short liveScore;
    private short historicalData;
    private int numberOfMatches;
    private Date latestMatch;

    public League(){

    }

    public League(int countryid, String leaguename){
        this.countryId=countryid;
        this.leagueName=leaguename;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(int leagueId) {
        this.leagueId = leagueId;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public short getFixture() {
        return fixture;
    }

    public void setFixture(short fixture) {
        this.fixture = fixture;
    }

    public short getIsCup() {
        return isCup;
    }

    public void setIsCup(short isCup) {
        this.isCup = isCup;
    }

    public short getLiveScore() {
        return liveScore;
    }

    public void setLiveScore(short liveScore) {
        this.liveScore = liveScore;
    }

    public short getHistoricalData() {
        return historicalData;
    }

    public void setHistoricalData(short historicalData) {
        this.historicalData = historicalData;
    }

    public int getNumberOfMatches() {
        return numberOfMatches;
    }

    public void setNumberOfMatches(int numberOfMatches) {
        this.numberOfMatches = numberOfMatches;
    }

    public Date getLatestMatch() {
        return latestMatch;
    }

    public void setLatestMatch(Date latestMatch) {
        this.latestMatch = latestMatch;
    }

    @Override
    public String toString() {
        return "League{" +
                "id=" + id +
                ", leagueId=" + leagueId +
                ", countryId=" + countryId +
                ", leagueName='" + leagueName + '\'' +
                ", fixture=" + fixture +
                ", isCup=" + isCup +
                ", liveScore=" + liveScore +
                ", historicalData=" + historicalData +
                ", numberOfMatches=" + numberOfMatches +
                ", latestMatch=" + latestMatch +
                '}';
    }
}
