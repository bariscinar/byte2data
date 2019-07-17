package net.byte2data.score.item;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by barisci on x1/27/16.
 *
 <Player>
 <Id>11003</Id>
 <Name>Ole Amund Sveen</Name>
 <Nationality>Norway</Nationality>
 <Position>Midfielder</Position>
 <Team_Id>1023</Team_Id>
 <PlayerNumber>19</PlayerNumber>
 <DateOfBirth>1990-01-05T00:00:00+00:00</DateOfBirth>
 <DateOfSigning>2016-01-01T00:00:00+00:00</DateOfSigning>
 <Signing>Signed</Signing>
 </Player>
 <AccountInformation>
 Data requested at 4/5/2016 10:39:41 AM from 212.174.34.82, Username: hbaris.cinar. Your current supscription runs out on x1/30/2017 8:36:31 PM.
 </AccountInformation>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="Player")
public class Player {

    @XmlElement(name="Id")
    private int id;

    @XmlElement(name="Name")
    private String name;

    @XmlElement(name="Height")
    private String height;

    @XmlElement(name="Weight")
    private String weight;

    @XmlElement(name="Nationality")
    private String nationality;

    @XmlElement(name="Position")
    private String position;

    @XmlElement(name="Team_Id")
    private int teamId;

    @XmlElement(name="LoanTo")
    private int loanTo;

    @XmlElement(name="PlayerNumber")
    private int playerNumber;

    @XmlElement(name="DateOfBirth")
    private String dateOfBirth;

    @XmlElement(name="DateOfSigning")
    private String dateOfSigning;

    @XmlElement(name="Signing")
    private String signing;

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

    //Nationality
    public String getNationality() {
        try{
            return this.nationality.trim().toUpperCase();
        }catch(Exception ex){
            System.out.println("Get Nationality Exception: " + ex);
        }
        return null;
    }
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    //Position
    public String getPosition() {
        try{
            return this.position.trim().toUpperCase();
        }catch(Exception ex){
            System.out.println("Get Position Exception: " + ex);
        }
        return null;
    }
    public void setPosition(String position) {
        this.position = position;
    }

    //Team ID
    public int getTeamId() {
        try{
            return this.teamId;
        }catch(Exception ex){
            System.out.println("Get Team ID Exception: " + ex);
        }
        return 0;
    }
    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    //Player Number
    public int getPlayerNumber() {
        try{
            return this.playerNumber;
        }catch(Exception ex){
            System.out.println("Get Player Number Exception: " + ex);
        }
        return 0;
    }
    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    //Date Of Birth
    public String getDateOfBirth() {
        try{
            if(this.dateOfBirth.contains("'+'"))
                return this.dateOfBirth.substring(0,this.dateOfBirth.indexOf("+"));
            return this.dateOfBirth;
        }catch(Exception ex){
            //System.out.println("Get Date Of Birth Exception: " + ex);
        }
        return null;
    }
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    //Date Of Signing
    public String getDateOfSigning() {
        try{
            if(this.dateOfSigning.contains("'+'"))
                return this.dateOfSigning.substring(0,this.dateOfSigning.indexOf("+"));
            return this.dateOfSigning;
        }catch(Exception ex){
            //System.out.println("Get Date Of Signing Exception: " + ex);
        }
        return null;
    }
    public void setDateOfSigning(String dateOfSigning) {
        this.dateOfSigning = dateOfSigning;
    }

    //Signing
    public String getSigning() {
        try{
            return this.signing.trim().toUpperCase();
        }catch(Exception ex){
            //System.out.println("Get Signing Exception: " + ex);
        }
        return null;
    }
    public void setSigning(String signing) {
        this.signing = signing;
    }

    //Height
    public String getHeight() {
        try{
            return this.height;
        }catch(Exception ex){
            System.out.println("Get Height Exception: " + ex);
        }
        return null;
    }
    public void setHeight(String height) {
        this.height = height;
    }

    //Weight
    public String getWeight() {
        try{
            return this.weight;
        }catch(Exception ex){
            System.out.println("Get Weight Exception: " + ex);
        }
        return null;
    }
    public void setWeight(String weight) {
        this.weight = weight;
    }

    //Loan To
    public int getLoanTo() {
        try{
            return this.loanTo;
        }catch(Exception ex){
            System.out.println("Get LoanTo Exception: " + ex);
        }
        return 0;
    }
    public void setLoanTo(int loanTo) {
        this.loanTo = loanTo;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", height='" + getHeight() + '\'' +
                ", weight='" + getWeight() + '\'' +
                ", nationality='" + getNationality() + '\'' +
                ", position='" + getPosition() + '\'' +
                ", teamId=" + getTeamId() +
                ", loanTo=" + getLoanTo() +
                ", playerNumber=" + getPlayerNumber() +
                ", dateOfBirth='" + getDateOfBirth() + '\'' +
                ", dateOfSigning='" + getDateOfSigning() + '\'' +
                ", signing='" + getSigning() + '\'' +
                '}';
    }
}
