package net.byte2data.score.cover;

import net.byte2data.score.item.Team;

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
public class Teams {
    @XmlElement(name="Team")
    private ArrayList<Team> teams;
    @XmlElement(name="AccountInformation")
    private String accountInformation;

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public void setTeams(ArrayList<Team> teams) {
        this.teams = teams;
    }

    public String getAccountsInformation() {
        return accountInformation;
    }

    public void setAccountInformation(String accountInformation) {
        this.accountInformation = accountInformation;
    }
}
