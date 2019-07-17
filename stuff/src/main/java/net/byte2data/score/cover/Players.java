package net.byte2data.score.cover;

import net.byte2data.score.item.Player;

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
public class Players {

    @XmlElement(name="Player")
    private ArrayList<Player> players;
    @XmlElement(name="AccountInformation")
    private String accountInformation;


    public ArrayList<Player> getPlayers() {
        return players;
    }
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public String getAccountInformation() {
        return accountInformation;
    }
    public void setAccountInformation(String accountInformation) {
        this.accountInformation = accountInformation;
    }

    @Override
    public String toString() {
        return "Players{" +
                "players=" + players +
                ", accountInformation='" + accountInformation + '\'' +
                '}';
    }
}
