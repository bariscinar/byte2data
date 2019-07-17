package net.byte2data.score.item;

import javax.xml.bind.annotation.*;

/**
 * Created by barisci on x1/25/16.
 *
 <TeamLeagueStanding xmlns="http://xmlsoccer.com/LeagueStanding">
 <Team>Real Madrid</Team>
 <Team_Id>154</Team_Id>
 <Played>6</Played>
 <PlayedAtHome>3</PlayedAtHome>
 <PlayedAway>3</PlayedAway>
 <Won>6</Won>
 <Draw>0</Draw>
 <Lost>0</Lost>
 <Goals_For>16</Goals_For>
 <Goals_Against>2</Goals_Against>
 <Goal_Difference>14</Goal_Difference>
 <Points>18</Points>
 </TeamLeagueStanding>
 *
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="TeamLeagueStanding")

public class TeamStanding {

    @XmlElement(name="Team", namespace="http://xmlsoccer.com/LeagueStanding")
    private String team;

    @XmlElement(name="Team_Id", namespace="http://xmlsoccer.com/LeagueStanding")
    private int id;

    @XmlElement(name="Played", namespace="http://xmlsoccer.com/LeagueStanding")
    private int played;

    @XmlElement(name="PlayedAtHome", namespace="http://xmlsoccer.com/LeagueStanding")
    private int playedAtHome;

    @XmlElement(name="PlayedAway", namespace="http://xmlsoccer.com/LeagueStanding")
    private int playedAway;

    @XmlElement(name="Won", namespace="http://xmlsoccer.com/LeagueStanding")
    private int won;

    @XmlElement(name="Draw", namespace="http://xmlsoccer.com/LeagueStanding")
    private int draw;

    @XmlElement(name="Lost", namespace="http://xmlsoccer.com/LeagueStanding")
    private int lost;

    @XmlElement(name="Goals_For", namespace="http://xmlsoccer.com/LeagueStanding")
    private int goalFor;

    @XmlElement(name="Goals_Against", namespace="http://xmlsoccer.com/LeagueStanding")
    private int goalAgainst;

    @XmlElement(name="NumberOfShots", namespace="http://xmlsoccer.com/LeagueStanding")
    private int numberOfShot;

    @XmlElement(name="RedCards", namespace="http://xmlsoccer.com/LeagueStanding")
    private int redCard;

    @XmlElement(name="YellowCards", namespace="http://xmlsoccer.com/LeagueStanding")
    private int yellowCard;

    @XmlElement(name="Goal_Difference", namespace="http://xmlsoccer.com/LeagueStanding")
    private int goalDifference;

    @XmlElement(name="Points", namespace="http://xmlsoccer.com/LeagueStanding")
    private int point;

    //Team_Id
    public int getId() {
        try{
            return this.id;
        }catch(Exception ex){
            System.out.println("Get Id Exception: " + ex);
        }
        return 0;
    }
    public void setId(int id) {
        this.id = id;
    }

    //Team
    public String getTeam() {
        try{
            if(null!=this.team){
                return this.team.trim().toUpperCase();
            }
        }catch(Exception ex){
            System.out.println("Get Team Exception: " + ex);
        }
        return null;
    }
    public void setTeam(String team) {
        this.team = team.trim().toUpperCase();
    }

    //Played
    public int getPlayed() {
        try{
            return this.played;
        }catch(Exception ex){
            System.out.println("Get Played Exception: " + ex);
        }
        return -1;
    }
    public void setPlayed(int played) {
        this.played = played;
    }

    //PlayedAtHome
    public int getPlayedAtHome() {
        try{
            return this.playedAtHome;
        }catch(Exception ex){
            System.out.println("Get Played At Home Exception: " + ex);
        }
        return -1;
    }
    public void setPlayedAtHome(int playedAtHome) {
        this.playedAtHome = playedAtHome;
    }

    //PlayedAway
    public int getPlayedAway() {
        try{
            return this.playedAway;
        }catch(Exception ex){
            System.out.println("Get Played Away Exception: " + ex);
        }
        return -1;
    }
    public void setPlayedAway(int playedAway) {
        this.playedAway = playedAway;
    }

    //Won
    public int getWon() {
        try{
            return this.won;
        }catch(Exception ex){
            System.out.println("Get Won Exception: " + ex);
        }
        return -1;
    }
    public void setWon(int won) {
        this.won = won;
    }

    //Draw
    public int getDraw() {
        try{
            return this.draw;
        }catch(Exception ex){
            System.out.println("Get Draw Exception: " + ex);
        }
        return -1;
    }
    public void setDraw(int draw) {
        this.draw = draw;
    }

    //Lost
    public int getLost() {
        try{
            return this.lost;
        }catch(Exception ex){
            System.out.println("Get Lost Exception: " + ex);
        }
        return -1;
    }
    public void setLost(int lost) {
        this.lost = lost;
    }

    //Goals For
    public int getGoalFor() {
        try{
            return this.goalFor;
        }catch(Exception ex){
            System.out.println("Get Goal For Exception: " + ex);
        }
        return -1;
    }
    public void setGoalFor(int goalFor) {
        this.goalFor = goalFor;
    }

    //Goals Against
    public int getGoalAgainst() {
        try{
            return this.goalAgainst;
        }catch(Exception ex){
            System.out.println("Get Goal Against Exception: " + ex);
        }
        return -1;
    }
    public void setGoalAgainst(int goalAgainst) {
        this.goalAgainst = goalAgainst;
    }

    //Goal Difference
    public int getGoalDifference() {
        try{
            return this.goalDifference;
        }catch(Exception ex){
            System.out.println("Get Goal Difference Exception: " + ex);
        }
        return -1;
    }
    public void setGoalDifference(int goalDifference) {
        this.goalDifference = goalDifference;
    }

    //POINT
    public int getPoint() {
        try{
            return this.point;
        }catch(Exception ex){
            System.out.println("Get point Exception: " + ex);
        }
        return -1;
    }
    public void setPoint(int point) {
        this.point = point;
    }

    //NUMBER OF SHOT
    public int getNumberOfShot() {
        try{
            return this.numberOfShot;
        }catch(Exception ex){
            System.out.println("Get Number Of Shot Exception: " + ex);
        }
        return -1;
    }
    public void setNumberOfShot(int numberOfShot) {
        this.numberOfShot = numberOfShot;
    }

    //RED CARD
    public int getRedCard() {
        try{
            return this.redCard;
        }catch(Exception ex){
            System.out.println("Get Red Card Exception: " + ex);
        }
        return -1;
    }
    public void setRedCard(int redCard) {
        this.redCard = redCard;
    }

    //YELLOW CARD
    public int getYellowCard() {
        try{
            return this.yellowCard;
        }catch(Exception ex){
            System.out.println("Get Yellow Card Exception: " + ex);
        }
        return -1;
    }
    public void setYellowCard(int yellowCard) {
        this.yellowCard=yellowCard;
    }

    @Override
    public String toString() {
        return "TeamStanding{" +
                "team='" + getTeam() + '\'' +
                ", id=" + getId() +
                ", played=" + getPlayed() +
                ", playedAtHome=" + getPlayedAtHome() +
                ", playedAway=" + getPlayedAway() +
                ", won=" + getWon() +
                ", draw=" + getDraw() +
                ", lost=" + getLost() +
                ", goalFor=" + getGoalFor() +
                ", goalAgainst=" + getGoalAgainst() +
                ", numberOfShot=" + getNumberOfShot() +
                ", redCard=" + getRedCard() +
                ", yellowCard=" + getYellowCard() +
                ", goalDifference=" + getGoalDifference() +
                ", point=" + getPoint() +
                '}';
    }
}
