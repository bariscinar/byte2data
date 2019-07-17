package net.byte2data.score.cover;

import net.byte2data.score.item.TeamStanding;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

/**
 * Created by barisci on x1/25/16.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "XMLSOCCER.COM")
public class LeagueStanding {
    @XmlElement(name="TeamLeagueStanding", namespace = "http://xmlsoccer.com/LeagueStanding")
    private ArrayList<TeamStanding> teamTeamStandings;
    @XmlElement(name="AccountInformation")
    private String accountInformation;

    public ArrayList<TeamStanding> getTeamTeamStandings() {
        return teamTeamStandings;
    }

    public void setTeamTeamStandings(ArrayList<TeamStanding> teamTeamStandings) {
        this.teamTeamStandings = teamTeamStandings;
    }

    public String getAccountsInformation() {
        return accountInformation;
    }

    public void setAccountInformation(String accountInformation) {
        this.accountInformation = accountInformation;
    }
}
