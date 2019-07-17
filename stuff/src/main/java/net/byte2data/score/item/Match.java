package net.byte2data.score.item;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by barisci on x1/26/16.
 *
 <Match>
 <Id>94688</Id>
 <FixtureMatch_Id>357021</FixtureMatch_Id>
 <Date>2016-04-13T16:30:00+00:00</Date>
 <Round>26</Round>
 <Spectators>37674</Spectators>
 <League>Süper Lig</League>
 <HomeTeam>Galatasaray</HomeTeam>
 <HomeTeam_Id>220</HomeTeam_Id>
 <HomeCorners>0</HomeCorners>
 <HomeGoals>0</HomeGoals>
 <HalfTimeHomeGoals>0</HalfTimeHomeGoals>
 <HomeShots>0</HomeShots>
 <HomeShotsOnTarget>0</HomeShotsOnTarget>
 <HomeGoalDetails/>
 <HomeLineupGoalkeeper>Fernando Muslera</HomeLineupGoalkeeper>
 <HomeLineupDefense>
 Olcan Adin; Semih Kaya; Jason Denayer; Hakan Kadir Balta;
 </HomeLineupDefense>
 <HomeLineupMidfield>
 Wesley Sneijder; Emre Colak; Selcuk Inan; Ryan Donk; Yasin Oztekin;
 </HomeLineupMidfield>
 <HomeLineupForward>Lukas Podolski;</HomeLineupForward>
 <HomeLineupSubstitutes>
 Umut Bulut; Martin Linnes; Koray Guenter; Cenk Gonen; Osman Tarik Camdal; Bilal Kisa; Sabri Sarioglu;
 </HomeLineupSubstitutes>
 <HomeLineupCoach>Jan Olde Riekerink;</HomeLineupCoach>
 <HomeYellowCards>0</HomeYellowCards>
 <HomeRedCards>0</HomeRedCards>
 <HomeSubDetails>
 82': in Bilal Kisa;82': out Selcuk Inan;77': in Koray Guenter;77': out Jason Denayer;62': in Sabri Sarioglu;62': out Emre Colak;
 </HomeSubDetails>
 <AwaySubDetails>
 80': out Alper Potuk;80': in Ozan Tufan;71': in Diego;71': out Volkan Sen;66': in Fernandao;66': out Robin van Persie;
 </AwaySubDetails>
 <HomeTeamFormation>4-2-3-x1</HomeTeamFormation>
 <AwayTeam>Fenerbahce</AwayTeam>
 <AwayTeam_Id>223</AwayTeam_Id>
 <AwayCorners>0</AwayCorners>
 <AwayGoals>0</AwayGoals>
 <HalfTimeAwayGoals>0</HalfTimeAwayGoals>
 <AwayShots>0</AwayShots>
 <AwayShotsOnTarget>0</AwayShotsOnTarget>
 <AwayGoalDetails/>
 <AwayLineupGoalkeeper>Volkan Demirel</AwayLineupGoalkeeper>
 <AwayLineupDefense>
 Gokhan Gonul; Simon Kjaer; Bruno Alves; Hasan Ali Kaldirim;
 </AwayLineupDefense>
 <AwayLineupMidfield>Souza; Mehmet Topal; Volkan Sen; Alper Potuk;</AwayLineupMidfield>
 <AwayLineupForward>Nani; Robin van Persie;</AwayLineupForward>
 <AwayLineupSubstitutes>
 Uygar Mert Zeybek; Fabiano; Sener Ozbayrakli; Ozan Tufan; Michal Kadlec; Fernandao; Diego;
 </AwayLineupSubstitutes>
 <AwayLineupCoach>Vitor Pereira;</AwayLineupCoach>
 <AwayYellowCards>0</AwayYellowCards>
 <AwayRedCards>0</AwayRedCards>
 <AwayTeamFormation>4-4-2</AwayTeamFormation>
 <HomeTeamYellowCardDetails>78': Sabri Sarioglu;66': Yasin Oztekin;</HomeTeamYellowCardDetails>
 <AwayTeamYellowCardDetails/>
 <HomeTeamRedCardDetails/>
 <AwayTeamRedCardDetails/>
 <Referee>Mete Kalkavan</Referee>
 </Match>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="Match")
public class Match {
    @XmlElement(name="Id")
    private int id;

    @XmlElement(name="FixtureMatch_Id")
    private int fixtureMatchId;

    @XmlElement(name="Date")
    private String date;

    @XmlElement(name="Round")
    private int round;

    @XmlElement(name="Spectators")
    private int spectator;

    @XmlElement(name="League")
    private String league;

    @XmlElement(name="HomeTeam")
    private String homeTeam;

    @XmlElement(name="HomeTeam_Id")
    private int homeTeamId;

    @XmlElement(name="HomeCorners")
    private int homeCorner;

    @XmlElement(name="HomeGoals")
    private int homeGoal;

    @XmlElement(name="HalfTimeHomeGoals")
    private int halfTimeHomeGoal;

    @XmlElement(name="HomeShots")
    private int homeShot;

    @XmlElement(name="HomeShotOnTarget")
    private int homeShotOnTarget;

    @XmlElement(name="HomeGoalDetails")
    private String homeGoalDetail;

    @XmlElement(name="HomeLineupGoalkeeper")
    private String homeLineupGoalkeeper;

    @XmlElement(name="HomeLineupDefense")
    private String homeLineupDefense;

    @XmlElement(name="HomeLineupMidfield")
    private String homeLineupMidfield;

    @XmlElement(name="HomeLineupForward")
    private String homeLineupForward;

    @XmlElement(name="HomeLineupSubstitutes")
    private String homeLineupSubstitute;

    @XmlElement(name="HomeLineupCoach")
    private String homeLineupCoach;

    @XmlElement(name="HomeYellowCards")
    private int homeYellowCard;

    @XmlElement(name="HomeTeamYellowCardDetails")
    private String homeTeamYellowCardDetail;

    @XmlElement(name="HomeRedCards")
    private int homeRedCard;

    @XmlElement(name="HomeTeamRedCardDetails")
    private String homeTeamRedCardDetail;

    @XmlElement(name="HomeSubDetails")
    private String homeSubDetail;

    @XmlElement(name="HomeTeamFormation")
    private String homeTeamFormation;

    @XmlElement(name="AwayTeam")
    private String awayTeam;

    @XmlElement(name="AwayTeam_Id")
    private int awayTeamId;

    @XmlElement(name="AwayCorners")
    private int awayCorner;

    @XmlElement(name="AwayGoals")
    private int awayGoal;

    @XmlElement(name="HalfTimeAwayGoals")
    private int halfTimeAwayGoal;

    @XmlElement(name="AwayShots")
    private int awayShot;

    @XmlElement(name="AwayShotOnTarget")
    private int awayShotOnTarget;

    @XmlElement(name="AwayGoalDetails")
    private String awayGoalDetail;

    @XmlElement(name="AwayLineupGoalkeeper")
    private String awayLineupGoalkeeper;

    @XmlElement(name="AwayLineupDefense")
    private String awayLineupDefense;

    @XmlElement(name="AwayLineupMidfield")
    private String awayLineupMidfield;

    @XmlElement(name="AwayLineupForward")
    private String awayLineupForward;

    @XmlElement(name="AwayLineupSubstitutes")
    private String awayLineupSubstitute;

    @XmlElement(name="AwayLineupCoach")
    private String awayLineupCoach;

    @XmlElement(name="AwayYellowCards")
    private int awayYellowCard;

    @XmlElement(name="AwayTeamYellowCardDetails")
    private String awayTeamYellowCardDetail;

    @XmlElement(name="AwayRedCards")
    private int awayRedCard;

    @XmlElement(name="AwayTeamRedCardDetails")
    private String awayTeamRedCardDetail;

    @XmlElement(name="AwaySubDetails")
    private String awaySubDetail;

    @XmlElement(name="AwayTeamFormation")
    private String awayTeamFormation;

    @XmlElement(name="Referee")
    private String referee;

    @XmlElement(name="Group")
    private String group;

    @XmlElement(name="Group_Id")
    private int groupId;

    //Group
    public String getGroup() {
        try{
            if(null!=this.group){
                return this.group.trim().toUpperCase();
            }
        }catch(Exception ex){
            System.out.println("Get Group Exception: " + ex);
        }
        return null;
    }
    public void setGroup(String group) {
        this.group = group;
    }

    //Group_Id
    public int getGroupId() {
        try{
            return this.groupId;
        }catch(Exception ex){
            System.out.println("Get Group_Id Exception: " + ex);
        }
        return 0;
    }
    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    //FixtureMatch_Id
    public int getFixtureMatchId() {
        try{
            return this.fixtureMatchId;
        }catch(Exception ex){
            System.out.println("Get FixtureMatch_Id Exception: " + ex);
        }
        return 0;
    }
    public void setFixtureMatchId(int fixtureMatchId) {
        this.fixtureMatchId = fixtureMatchId;
    }

    //HomeCorners
    public int getHomeCorner() {
        try{
            return this.homeCorner;
        }catch(Exception ex){
            System.out.println("Get HomeCorners Exception: " + ex);
        }
        return 0;
    }
    public void setHomeCorner(int homeCorner) {
        this.homeCorner = homeCorner;
    }

    //HalfTimeHomeGoals
    public int getHalfTimeHomeGoal() {
        try{
            return this.halfTimeHomeGoal;
        }catch(Exception ex){
            System.out.println("Get HalfTimeHomeGoals Exception: " + ex);
        }
        return 0;
    }
    public void setHalfTimeHomeGoal(int halfTimeHomeGoal) {
        this.halfTimeHomeGoal = halfTimeHomeGoal;
    }

    //HomeShots
    public int getHomeShot() {
        try{
            return this.homeShot;
        }catch(Exception ex){
            System.out.println("Get HomeShots Exception: " + ex);
        }
        return 0;
    }
    public void setHomeShot(int homeShot) {
        this.homeShot = homeShot;
    }

    //HomeShotOnTarget
    public int getHomeShotOnTarget() {
        try{
            return this.homeShotOnTarget;
        }catch(Exception ex){
            System.out.println("Get HomeShotOnTarget Exception: " + ex);
        }
        return 0;
    }
    public void setHomeShotOnTarget(int homeShotOnTarget) {
        this.homeShotOnTarget = homeShotOnTarget;
    }

    //HomeYellowCards
    public int getHomeYellowCard() {
        try{
            return this.homeYellowCard;
        }catch(Exception ex){
            System.out.println("Get HomeYellowCards Exception: " + ex);
        }
        return 0;
    }
    public void setHomeYellowCard(int homeYellowCard) {
        this.homeYellowCard = homeYellowCard;
    }

    //HomeRedCards
    public int getHomeRedCard() {
        try{
            return this.homeRedCard;
        }catch(Exception ex){
            System.out.println("Get HomeRedCards Exception: " + ex);
        }
        return 0;
    }
    public void setHomeRedCard(int homeRedCard) {
        this.homeRedCard = homeRedCard;
    }

    //AwayCorners
    public int getAwayCorner() {
        try{
            return this.awayCorner;
        }catch(Exception ex){
            System.out.println("Get AwayCorners Exception: " + ex);
        }
        return 0;
    }
    public void setAwayCorner(int awayCorner) {
        this.awayCorner = awayCorner;
    }

    //HalfTimeAwayGoals
    public int getHalfTimeAwayGoal() {
        try{
            return this.halfTimeAwayGoal;
        }catch(Exception ex){
            System.out.println("Get HalfTimeAwayGoals Exception: " + ex);
        }
        return 0;
    }
    public void setHalfTimeAwayGoal(int halfTimeAwayGoal) {
        this.halfTimeAwayGoal = halfTimeAwayGoal;
    }

    //AwayShots
    public int getAwayShot() {
        try{
            return this.awayShot;
        }catch(Exception ex){
            System.out.println("Get AwayShots Exception: " + ex);
        }
        return 0;
    }
    public void setAwayShot(int awayShot) {
        this.awayShot = awayShot;
    }

    //AwayShotOnTarget
    public int getAwayShotOnTarget() {
        try{
            return this.awayShotOnTarget;
        }catch(Exception ex){
            System.out.println("Get AwayShotOnTarget Exception: " + ex);
        }
        return 0;
    }
    public void setAwayShotOnTarget(int awayShotOnTarget) {
        this.awayShotOnTarget = awayShotOnTarget;
    }

    //AwayYellowCards
    public int getAwayYellowCard() {
        try{
            return this.awayYellowCard;
        }catch(Exception ex){
            System.out.println("Get AwayYellowCards Exception: " + ex);
        }
        return 0;
    }
    public void setAwayYellowCard(int awayYellowCard) {
        this.awayYellowCard = awayYellowCard;
    }

    //AwayRedCards
    public int getAwayRedCard() {
        try{
            return this.awayRedCard;
        }catch(Exception ex){
            System.out.println("Get AwayRedCards Exception: " + ex);
        }
        return 0;
    }
    public void setAwayRedCard(int awayRedCard) {
        this.awayRedCard = awayRedCard;
    }

    //Referee
    public String getReferee() {
        try{
            if(null!=this.referee){
                return this.referee.trim().toUpperCase();
            }
        }catch(Exception ex){
            System.out.println("Get Referee Exception: " + ex);
        }
        return null;
    }
    public void setReferee(String referee) {
        this.referee = referee;
    }

    //ID
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

    //MATCH DATE
    public String getDate() {
        try{
            if(null!=this.date){
                if(this.date.contains("'+'"))
                    return this.date.substring(0,this.date.indexOf("+"));
                return this.date;
            }
        }catch(Exception ex){
            System.out.println("Get Match Date Exception: " + ex);
        }
        return null;

    }
    public void setDate(String date) {
        this.date = date;
    }

    //LEAGUE
    public String getLeague() {
        try{
            if(null!=this.league){
                return this.league.trim().toUpperCase();
            }
        }catch(Exception ex){
            System.out.println("Get League Exception: " + ex);
        }
        return null;
    }
    public void setLeague(String league) {
        this.league = league;
    }

    //ROUND
    public int getRound() {
        try{
            return round;
        }catch(Exception ex){
            System.out.println("Get Round Exception: " + ex);
        }
        return 0;
    }
    public void setRound(int round) {
        this.round = round;
    }

    //SPECTRATORS
    public int getSpectator() {
        try{
            return spectator;
        }catch(Exception ex){
            System.out.println("Get Id Exception: " + ex);
        }
        return 0;
    }
    public void setSpectator(int spectators) {
        this.spectator = spectators;
    }

    //HOME TEAM
    public String getHomeTeam() {
        try{
            if(null!=this.homeTeam){
                return this.homeTeam.trim().toUpperCase();
            }
        }catch(Exception ex){
            System.out.println("Get HOME TEAM Exception: " + ex);
        }
        return null;
    }
    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    //HOME TEAM ID
    public int getHomeTeamId() {
        try{
            return this.homeTeamId;
        }catch(Exception ex){
            System.out.println("Get HOME TEAM ID Exception: " + ex);
        }
        return 0;
    }
    public void setHomeTeamId(int homeTeam_Id) {
        this.homeTeamId = homeTeam_Id;
    }

    //AWAY TEAM
    public String getAwayTeam() {
        try{
            if(null!=this.awayTeam){
                return this.awayTeam.trim().toUpperCase();
            }
        }catch(Exception ex){
            System.out.println("Get AWAY TEAM Exception: " + ex);
        }
        return null;
    }
    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    //AWAY TEAM ID
    public int getAwayTeamId() {
        try{
            return this.awayTeamId;
        }catch(Exception ex){
            System.out.println("Get Away Team Id Exception: " + ex);
        }
        return 0;
    }
    public void setAwayTeamId(int awayTeam_Id) {
        this.awayTeamId = awayTeam_Id;
    }

    /*
    //TIME
    public String getTime() {
        try{
            if(null!=this.time){
                return this.time.trim().toUpperCase();
            }
        }catch(Exception ex){
            System.out.println("Get Time Exception: " + ex);
        }
        return null;
    }
    public void setTime(String time) {
        this.time = time;
    }
    */
    //HOME GOALS
    public int getHomeGoal() {
        try{
            return this.homeGoal;
        }catch(Exception ex){
            System.out.println("Get Home Goals Exception: " + ex);
        }
        return 0;
    }
    public void setHomeGoal(int homeGoals) {
        this.homeGoal = homeGoals;
    }

    //AWAY GOAL DETAILS
    public int getAwayGoal() {
        try{
            return this.awayGoal;
        }catch(Exception ex){
            System.out.println("Get Away Goals Exception: " + ex);
        }
        return 0;
    }
    public void setAwayGoal(int awayGoals) {
        this.awayGoal = awayGoals;
    }

    //HOME GOAL DETAILS
    public String getHomeGoalDetail() {
        try{
            if(null!=this.homeGoalDetail){
                return this.homeGoalDetail.trim().toUpperCase();
            }
        }catch(Exception ex){
            System.out.println("Get Home Goal Details Exception: " + ex);
        }
        return null;
    }
    public void setHomeGoalDetail(String homeGalDetails) {
        this.homeGoalDetail = homeGalDetails;
    }

    //AWAY GOAL DETAILS
    public String getAwayGoalDetail() {
        try{
            if(null!=this.awayGoalDetail){
                return this.awayGoalDetail.trim().toUpperCase();
            }
        }catch(Exception ex){
            System.out.println("Get Away Goal Details Exception: " + ex);
        }
        return null;
    }
    public void setAwayGoalDetail(String awayGoalDetails) {
        this.awayGoalDetail = awayGoalDetails;
    }

    //HOME LINEUP GOALKEEPER
    public String getHomeLineupGoalkeeper() {
        try{
            if(null!=this.homeLineupGoalkeeper){
                return this.homeLineupGoalkeeper.trim().toUpperCase();
            }
        }catch(Exception ex){
            System.out.println("Get Home Lineup Goalkeeper Exception: " + ex);
        }
        return null;
    }
    public void setHomeLineupGoalkeeper(String homeLineupGoalkeeper) {
        this.homeLineupGoalkeeper = homeLineupGoalkeeper;
    }

    //AWAY LINEUP GOALKEEPER
    public String getAwayLineupGoalkeeper() {
        try{
            if(null!=this.awayLineupGoalkeeper){
                return this.awayLineupGoalkeeper.trim().toUpperCase();
            }
        }catch(Exception ex){
            System.out.println("Get Away Lineup Goalkeeper Exception: " + ex);
        }
        return null;
    }
    public void setAwayLineupGoalkeeper(String awayLineupGoalkeeper) {
        this.awayLineupGoalkeeper = awayLineupGoalkeeper;
    }

    //HOME LINEUP DEFENSE
    public String getHomeLineupDefense() {
        try{
            if(null!=this.homeLineupDefense){
                return this.homeLineupDefense.trim().toUpperCase();
            }
        }catch(Exception ex){
            System.out.println("Get Home Lineup Defense Exception: " + ex);
        }
        return null;
    }
    public void setHomeLineupDefense(String homeLineupDefense) {
        this.homeLineupDefense = homeLineupDefense;
    }

    //AWAY LINEUP DEFENSE
    public String getAwayLineupDefense() {
        try{
            if(null!=this.awayLineupDefense){
                return this.awayLineupDefense.trim().toUpperCase();
            }
        }catch(Exception ex){
            System.out.println("Get Away Lineup Defense Exception: " + ex);
        }
        return null;
    }
    public void setAwayLineupDefense(String awayLineupDefense) {
        this.awayLineupDefense = awayLineupDefense;
    }

    //HOME LINEUP MIDFIELD
    public String getHomeLineupMidfield() {
        try{
            if(null!=this.homeLineupMidfield){
                return this.homeLineupMidfield.trim().toUpperCase();
            }
        }catch(Exception ex){
            System.out.println("Get Home Lineup Midfield Exception: " + ex);
        }
        return null;
    }
    public void setHomeLineupMidfield(String homeLineupMidfield) {
        this.homeLineupMidfield = homeLineupMidfield;
    }

    //AWAY LINEUP MIDFIELD
    public String getAwayLineupMidfield() {
        try{
            if(null!=this.awayLineupMidfield){
                return this.awayLineupMidfield.trim().toUpperCase();
            }
        }catch(Exception ex){
            System.out.println("Get Away Lineup Midfield Exception: " + ex);
        }
        return null;
    }
    public void setAwayLineupMidfield(String awayLineupMidfield) {
        this.awayLineupMidfield = awayLineupMidfield;
    }

    //HOME LINEUP FORWARD
    public String getHomeLineupForward() {
        try{
            if(null!=this.homeLineupForward){
                return this.homeLineupForward.trim().toUpperCase();
            }
        }catch(Exception ex){
            System.out.println("Get Home Lineup Forward Exception: " + ex);
        }
        return null;
    }
    public void setHomeLineupForward(String homeLineupForward) {
        this.homeLineupForward = homeLineupForward;
    }

    //AWAY LINEUP FORWARD
    public String getAwayLineupForward() {
        try{
            if(null!=this.awayLineupForward){
                return this.awayLineupForward.trim().toUpperCase();
            }
        }catch(Exception ex){
            System.out.println("Get Away Lineup Forward Exception: " + ex);
        }
        return null;
    }
    public void setAwayLineupForward(String awayLineupForward) {
        this.awayLineupForward = awayLineupForward;
    }

    //HOME LINEUP SUBSTITUTES
    public String getHomeLineupSubstitute() {
        try{
            if(null!=this.homeLineupSubstitute){
                return this.homeLineupSubstitute.trim().toUpperCase();
            }
        }catch(Exception ex){
            System.out.println("Get Home Lineup Substitutes Exception: " + ex);
        }
        return null;
    }
    public void setHomeLineupSubstitute(String homeLineupSubstitutes) {
        this.homeLineupSubstitute = homeLineupSubstitutes;
    }

    //AWAY LINEUP SUBSTITUTES
    public String getAwayLineupSubstitute() {
        try{
            if(null!=this.awayLineupSubstitute){
                return this.awayLineupSubstitute.trim().toUpperCase();
            }
        }catch(Exception ex){
            System.out.println("Get Away Lineup Substitutes Exception: " + ex);
        }
        return null;
    }
    public void setAwayLineupSubstitute(String awayLineupSubstitutes) {
        this.awayLineupSubstitute = awayLineupSubstitutes;
    }

    //HOME LINEUP COACH
    public String getHomeLineupCoach() {
        try{
            if(null!=this.homeLineupCoach){
                return this.homeLineupCoach.trim().toUpperCase();
            }
        }catch(Exception ex){
            System.out.println("Get Home Lineup Coach Exception: " + ex);
        }
        return null;
    }
    public void setHomeLineupCoach(String homeLineupCoach) {
        this.homeLineupCoach = homeLineupCoach;
    }

    //AWAY LINEUP COACH
    public String getAwayLineupCoach() {
        try{
            if(null!=this.awayLineupCoach){
                return this.awayLineupCoach.trim().toUpperCase();
            }
        }catch(Exception ex){
            System.out.println("Get Away Lineup Coach Exception: " + ex);
        }
        return null;
    }
    public void setAwayLineupCoach(String awayLineupCoach) {
        this.awayLineupCoach = awayLineupCoach;
    }

    //HOME SUB DETAILS
    public String getHomeSubDetail() {
        try{
            if(null!=this.homeSubDetail){
                return this.homeSubDetail.trim().toUpperCase();
            }
        }catch(Exception ex){
            System.out.println("Get Home Sub Details Exception: " + ex);
        }
        return null;
    }
    public void setHomeSubDetail(String homeSubDetails){
        this.homeSubDetail = homeSubDetails;
    }

    //AWAY SUB DETAILS
    public String getAwaySubDetail() {
        try{
            if(null!=this.awaySubDetail){
                return this.awaySubDetail.trim().toUpperCase();
            }
        }catch(Exception ex){
            System.out.println("Get Away Sub Details Exception: " + ex);
        }
        return null;
    }
    public void setAwaySubDetail(String awaySubDetails) {
        this.awaySubDetail = awaySubDetails;
    }

    //HOME TEAM FORMATION
    public String getHomeTeamFormation() {
        try{
            if(null!=this.homeTeamFormation){
                return this.homeTeamFormation.trim().toUpperCase();
            }
        }catch(Exception ex){
            System.out.println("Get Home Team Formation Exception: " + ex);
        }
        return null;
    }
    public void setHomeTeamFormation(String homeTeamFormation) {
        this.homeTeamFormation = homeTeamFormation;
    }

    //AWAY TEAM FORMATION
    public String getAwayTeamFormation() {
        try{
            if(null!=this.awayTeamFormation){
                return this.awayTeamFormation.trim().toUpperCase();
            }
        }catch(Exception ex){
            System.out.println("Get Away Team Formation Exception: " + ex);
        }
        return null;
    }
    public void setAwayTeamFormation(String awayTeamFormation) {
        this.awayTeamFormation = awayTeamFormation;
    }

    /*
    //LOCATION
    public String getLocation() {
        try{
            if(null!=this.location){
                return this.location.trim().toUpperCase();
            }
        }catch(Exception ex){
            System.out.println("Get Location Exception: " + ex);
        }
        return null;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    */

    //HOME TEAM YELLOW CARD DETAILS
    public String getHomeTeamYellowCardDetail() {
        try{
            if(null!=this.homeTeamYellowCardDetail){
                return this.homeTeamYellowCardDetail.trim().toUpperCase();
            }
        }catch(Exception ex){
            System.out.println("Get Home Team Yellow Card Details Exception: " + ex);
        }
        return null;
    }
    public void setHomeTeamYellowCardDetail(String homeTeamYellowCardDetails) {
        this.homeTeamYellowCardDetail = homeTeamYellowCardDetails;
    }

    //AWAY TEAM YELLOW CARD DETAILS
    public String getAwayTeamYellowCardDetail() {
        try{
            if(null!=this.awayTeamYellowCardDetail){
                return this.awayTeamYellowCardDetail.trim().toUpperCase();
            }
        }catch(Exception ex){
            System.out.println("Get Away Team Yellow Card Details Exception: " + ex);
        }
        return null;
    }
    public void setAwayTeamYellowCardDetail(String awayTeamYellowCardDetails) {
        this.awayTeamYellowCardDetail = awayTeamYellowCardDetails;
    }

    //HOME TEAM RED CARD DETAILS
    public String getHomeTeamRedCardDetail() {
        try{
            if(null!=this.homeTeamRedCardDetail){
                return this.homeTeamRedCardDetail.trim().toUpperCase();
            }
        }catch(Exception ex){
            System.out.println("Get Hpme Team Red Card Details Exception: " + ex);
        }
        return null;
    }
    public void setHomeTeamRedCardDetail(String homeTeamRedCardDetails) {
        this.homeTeamRedCardDetail = homeTeamRedCardDetails;
    }

    //AWAY TEAM RED CARD DETAILS
    public String getAwayTeamRedCardDetail() {
        try{
            if(null!=this.awayTeamRedCardDetail){
                return this.awayTeamRedCardDetail.trim().toUpperCase();
            }
        }catch(Exception ex){
            System.out.println("Get Away Team Red Card Details Exception: " + ex);
        }
        return null;
    }
    public void setAwayTeamRedCardDetail(String awayTeamRedCardDetails) {
        this.awayTeamRedCardDetail = awayTeamRedCardDetails;
    }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + getId() +
                ", fixtureMatchId=" + getFixtureMatchId() +
                ", date='" + getDate() + '\'' +
                ", round=" + getRound() +
                ", spectator=" + getSpectator() +
                ", league='" + getLeague() + '\'' +
                ", homeTeam='" + getHomeTeam() + '\'' +
                ", homeTeamId=" + getHomeTeamId() +
                ", homeCorner=" + getHomeCorner() +
                ", homeGoal=" + getHomeGoal() +
                ", halfTimeHomeGoal=" + getHalfTimeHomeGoal() +
                ", homeShot=" + getHomeShot() +
                ", homeShotOnTarget=" + getHomeShotOnTarget() +
                ", homeGoalDetail='" + getHomeGoalDetail() + '\'' +
                ", homeLineupGoalkeeper='" + getHomeLineupGoalkeeper() + '\'' +
                ", homeLineupDefense='" + getHomeLineupDefense() + '\'' +
                ", homeLineupMidfield='" + getHomeLineupMidfield() + '\'' +
                ", homeLineupForward='" + getHomeLineupForward() + '\'' +
                ", homeLineupSubstitute='" + getHomeLineupSubstitute() + '\'' +
                ", homeLineupCoach='" + getHomeLineupCoach() + '\'' +
                ", homeYellowCard=" + getHomeYellowCard() +
                ", homeTeamYellowCardDetail='" + getHomeTeamYellowCardDetail() + '\'' +
                ", homeRedCard=" + getHomeRedCard() +
                ", homeTeamRedCardDetail='" + getHomeTeamRedCardDetail() + '\'' +
                ", homeSubDetail='" + getHomeSubDetail() + '\'' +
                ", homeTeamFormation='" + getHomeTeamFormation() + '\'' +
                ", awayTeam='" + getAwayTeam() + '\'' +
                ", awayTeamId=" + getAwayTeamId() +
                ", awayCorner=" + getAwayCorner()+
                ", awayGoal=" + getAwayGoal() +
                ", halfTimeAwayGoal=" + getHalfTimeAwayGoal() +
                ", awayShot=" + getAwayShot() +
                ", awayShotOnTarget=" + getAwayShotOnTarget() +
                ", awayGoalDetail='" + getAwayGoalDetail() + '\'' +
                ", awayLineupGoalkeeper='" + getAwayLineupGoalkeeper() + '\'' +
                ", awayLineupDefense='" + getAwayLineupDefense() + '\'' +
                ", awayLineupMidfield='" + getAwayLineupMidfield() + '\'' +
                ", awayLineupForward='" + getAwayLineupForward() + '\'' +
                ", awayLineupSubstitute='" + getAwayLineupSubstitute() + '\'' +
                ", awayLineupCoach='" + getAwayLineupCoach() + '\'' +
                ", awayYellowCard=" + getAwayYellowCard() +
                ", awayTeamYellowCardDetail='" + getAwayTeamYellowCardDetail() + '\'' +
                ", awayRedCard=" + getAwayRedCard() +
                ", awayTeamRedCardDetail='" + getAwayTeamRedCardDetail() + '\'' +
                ", awaySubDetail='" + getAwaySubDetail() + '\'' +
                ", awayTeamFormation='" + getAwayTeamFormation() + '\'' +
                ", referee='" + getReferee() + '\'' +
                '}';
    }
}
