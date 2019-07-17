package net.byte2data.score.cover;

import net.byte2data.score.item.Match;

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
public class Matches {
    @XmlElement(name="Match")
    private ArrayList<Match> matches;
    @XmlElement(name="AccountInformation")
    private String accountInformation;

    public ArrayList<Match> getMatches() {
        return matches;
    }

    public void setMatches(ArrayList<Match> matches) {
        this.matches = matches;
    }

    public String getAccountsInformation() {
        return accountInformation;
    }

    public void setAccountInformation(String accountInformation) {
        this.accountInformation = accountInformation;
    }
}
