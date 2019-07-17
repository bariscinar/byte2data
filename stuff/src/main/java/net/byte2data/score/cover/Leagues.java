package net.byte2data.score.cover;

import net.byte2data.score.item.League;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

/**
 * Created by barisci on x1/26/16.
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "XMLSOCCER.COM")
public class Leagues {

    @XmlElement(name="League")
    private ArrayList<League> leagues;
    @XmlElement(name="AccountInformation")
    private String accountInformation;

    @Override
    public String toString() {
        return "Leagues{" +
                "leagues:" + leagues +
                ", accountInformation:'" + accountInformation + '\'' +
                '}';
    }

    public ArrayList<League> getLeagues() {
        return leagues;
    }
    public void setLeaugues(ArrayList<League> leagues) {
        this.leagues = leagues;
    }

    public String getAccountInformation() {
        return accountInformation;
    }
    public void setAccountInformation(String accountInformation) {
        this.accountInformation = accountInformation;
    }

}
