package net.byte2data.score.item;

import javax.xml.bind.annotation.*;

/**
 * Created by barisci on x1/25/16.
 *
 <Team>
 <Team_Id>254</Team_Id>
 <Name>Falkirk</Name>
 <Country>Scotland</Country>
 <Stadium>Falkirk Stadium</Stadium>
 <HomePageURL>http://www.falkirkfc.co.uk/</HomePageURL>
 <WIKILink>http://en.wikipedia.org/wiki/Falkirk_F.C.</WIKILink>
 </Team>
 *
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="Team")
//@XmlType(propOrder = { "team_Id", "name", "country", "stadium", "homePageURL", "WIKILink" })
public class Team {
    @XmlElement(name="Team_Id")
    private int id;

    @XmlElement(name="Name")
    private String name;

    @XmlElement(name="Country")
    private String country;

    @XmlElement(name="Stadium")
    private String stadium;

    @XmlElement(name="HomePageURL")
    private String homePageURL;

    @XmlElement(name="WIKILink")
    private String wikiLink;

    //Id
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

    //Name
    public String getName() {
        try{
            if(null!=this.name){
                return this.name.trim().toUpperCase();
            }
        }catch(Exception ex){
            System.out.println("Get Name Exception: " + ex);
        }
        return null;
    }
    public void setName(String name) {
        this.name = name.trim().toUpperCase();
    }

    //Country
    public String getCountry() {
        try{
            if(null!=this.country){
                return country.trim().toUpperCase();
            }
        }catch(Exception ex){
            System.out.println("Get Country Exception: " + ex);
        }
        return null;
    }
    public void setCountry(String country) {
        this.country = country.trim().toUpperCase();
    }

    //Stadium
    public String getStadium() {
        try{
            if(null!=this.stadium){
                return stadium.trim().toUpperCase();
            }
        }catch(Exception ex){
            System.out.println("Get Stadium Exception: " + ex);
        }
        return null;
    }
    public void setStadium(String stadium) {
        this.stadium = stadium.trim().toUpperCase();
    }

    public String getHomePageURL() {
        try{
            if(null!=this.homePageURL){
                return homePageURL.trim();
            }
        }catch(Exception ex){
            System.out.println("Get Homepage Exception: " + ex);
        }
        return null;
    }
    public void setHomePageURL(String homePageURL) {
        this.homePageURL = homePageURL.trim();
    }

    public String getWIKILink() {
        try{
            if(null!=this.wikiLink){
                return wikiLink.trim();
            }
        }catch(Exception ex){
            System.out.println("Get Wiki Exception: " + ex);
        }
        return null;
    }
    public void setWIKILink(String wikiLink) {
        this.wikiLink = wikiLink.trim();
    }

    @Override
    public String toString() {
        return "Team{" +
                "Team_Id=" + getId() +
                ", Name='" + getName() + '\'' +
                ", Country='" + getCountry() + '\'' +
                ", Stadium='" + getStadium() + '\'' +
                ", HomePageURL='" + getHomePageURL() + '\'' +
                ", WIKILink='" + getWIKILink() + '\'' +
                '}';
    }
}
