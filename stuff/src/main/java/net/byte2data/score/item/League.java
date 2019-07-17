package net.byte2data.score.item;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by barisci on x1/27/16.
 *
 <League>
 <Id>x1</Id>
 <Name>English Premier League</Name>
 <Country>England</Country>
 <Historical_Data>Yes</Historical_Data>
 <Fixtures>Yes</Fixtures>
 <Livescore>Yes</Livescore>
 <NumberOfMatches>5930</NumberOfMatches>
 <LatestMatch>2016-01-24T17:00:00+00:00</LatestMatch>
 </League>
 *
 <AccountInformation>
 Data requested at x1/27/2016 2:03:45 PM from 212.174.34.82, Username: hbaris.cinar. You are correctly logged into the Demo-service, and only records from the Scottish Premier League can be extracted here. If no data is shown, no matches were found using the specified parameters. If you are using the livescore-method and no matches are returned, it is because no matches is currently being played in the Scottish Premier League.
 </AccountInformation>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="League")
public class League {

    @XmlElement(name="Id")
    private int id;

    @XmlElement(name="Name")
    private String name;

    @XmlElement(name="Country")
    private String country;

    @XmlElement(name="Historical_Data")
    private String historicalData;

    @XmlElement(name="Fixtures")
    private String fixtures;

    @XmlElement(name="Livescore")
    private String livescore;

    @XmlElement(name="IsCup")
    private String isCup;

    @XmlElement(name="NumberOfMatches")
    private String numberOfMatches;

    @XmlElement(name="LatestMatch")
    private String latestMatch;

    //Id
    public int getId() {
        try{
            return id;
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
                return this.country.trim().toUpperCase();
            }
        }catch(Exception ex){
            System.out.println("Get Country Exception: " + ex);
        }
        return null;
    }
    public void setCountry(String country) {
        this.country = country.trim().toUpperCase();
    }

    //Historical Data
    public int getHistoricalData() {
        try{
            if(null!=this.historicalData){
                if(this.historicalData.equalsIgnoreCase("yes")){
                    return 1;
                }
                return 2;
            }
        }catch(Exception ex){
            System.out.println("Get Historical Data Exception: " + ex);
        }
        return 0;

    }
    public void setHistoricalData(int historicalData) {
        if(historicalData==1){
            this.historicalData="yes";
        }else{
            this.historicalData="partial";
        }
    }

    //Fixtures
    public int getFixtures() {
        try{
            if(null!=this.fixtures){
                if(this.fixtures.equalsIgnoreCase("yes")){
                    return 1;
                }
                return 2;
            }
        }catch(Exception ex){
            System.out.println("Get Fixtures Exception: " + ex);
        }
        return 0;
    }
    public void setFixtures(int fixtures) {
        if(fixtures==1){
            this.fixtures="Yes";
        }else{
            this.fixtures="false";
        }
    }

    //Livescore
    public int getLivescore() {
        try{
            if(null!=this.livescore){
                if(this.livescore.equalsIgnoreCase("yes")){
                    return 1;
                }
                return 2;
            }
        }catch(Exception ex){
            System.out.println("Get Livescore Exception: " + ex);
        }
        return 0;
    }
    public void setLivescore(int livescore) {
        if(livescore==1){
            this.fixtures="yes";
        }else{
            this.livescore="false";
        }

    }

    //Is Cup
    public int getCup() {
        try{
            if(null!=this.isCup){
                if(this.isCup.equalsIgnoreCase("true")){
                    return 1;
                }
                return 2;
            }
        }catch(Exception ex){
            System.out.println("Get Cup Exception: " + ex);
        }
        return 0;
    }
    public void setCup(int cup) {
        if(cup==1){
            this.fixtures="true";
        }else{
            this.isCup="false";
        }
    }

    //Number Of Matches
    public int getNumberOfMatches() {
        try{
            if(null!=this.numberOfMatches)
                return Integer.parseInt(this.numberOfMatches);
        }catch(Exception ex){
            System.out.println("Get Number Of Matches Exception: " + ex);
        }
        return 0;
    }
    public void setNumberOfMatches(int numberOfMatches) {
        this.numberOfMatches = String.valueOf(numberOfMatches);
    }

    //Latest Match
    public String getLatestMatch() {
        try{
            if(null!=this.latestMatch){
                if(this.latestMatch.contains("'+'"))
                    return this.latestMatch.substring(0,this.latestMatch.indexOf("+"));
                return this.latestMatch;
            }
        }catch(Exception ex){
            System.out.println("Get Latest Match Exception: " + ex);
        }
        return null;
    }
    public void setLatestMatch(String latestMatch) {
        this.latestMatch = latestMatch;
    }

    @Override
    public String toString() {
        return "League{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", country='" + getCountry() + '\'' +
                ", historicalData='" + getHistoricalData() + '\'' +
                ", fixtures='" + getFixtures() + '\'' +
                ", livescore='" + getLivescore() + '\'' +
                ", isCup='" + getCup() + '\'' +
                ", numberOfMatches='" + getNumberOfMatches() + '\'' +
                ", latestMatch='" + getLatestMatch() + '\'' +
                '}';
    }
}
