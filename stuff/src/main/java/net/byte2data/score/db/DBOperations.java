package net.byte2data.score.db;

import net.byte2data.score.item.*;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by barisci on 11.04.2016.
 */
public class DBOperations {

    private Connection mysqlConnection;
    private PreparedStatement mysqlPreparedStatement;
    private ResultSet mysqlResultSet;

    private String JDBC_DRIVER;
    private String DB_URL;
    private String DB_USERNAME;
    private String DB_PASSWORD;

    private final static int currentSessionID=17;

    private String findCountrySQL = "select ID from SCOREPREDICTION.COUNTRY WHERE COUNTRYNAME=?";
    private String findLeagueByCountryIdAndLeagueNameSQL = "select ID from SCOREPREDICTION.LEAGUE WHERE (COUNTRYID=? AND LEAGUENAME=?)";
    private String findLeagueByLeagueNameSQL = "select ID from SCOREPREDICTION.LEAGUE WHERE LEAGUENAME=?";
    private String findPlayerByPlayerIdAndPlayerNameSQL = "select ID from SCOREPREDICTION.PLAYER WHERE (PLAYERID=? AND PLAYERNAME=?)";
    private String findMatchByFixtureIDSQL = "select ID from SCOREPREDICTION.MATCH WHERE MATCHID=?";

    private String findPlayerByPlayerNameAndTeamIdSQL = "select ID from SCOREPREDICTION.PLAYER WHERE ((PLAYER.PLAYERNAME LIKE ?) AND ((CURRENTTEAMID=?) OR (LOANTO=?)))";
    private String findPlayerByPlayerNamesAndTeamIdSQL = "select ID from SCOREPREDICTION.PLAYER WHERE (((PLAYER.FIRSTNAME = ?) OR (PLAYER.LASTNAME LIKE ?)) AND ((CURRENTTEAMID=?) OR (LOANTO=?)))";
    private String findPlayerByPlayerNameSQL = "select ID from SCOREPREDICTION.PLAYER WHERE (PLAYER.PLAYERNAME LIKE ?)";
    private String updatePlayerTeamByPlayerIdQL = "UPDATE SCOREPREDICTION.PLAYER SET CURRENTTEAMID=? WHERE PLAYER.PLAYERID=?";

    private String findMatchIdSQL = "select ID from SCOREPREDICTION.MATCH WHERE MATCHID=? AND LEAGUEID=?";
    private String findMatchEventIdIdSQL = "select ID from SCOREPREDICTION.MATCHEVENT WHERE MATCHID=? AND TEAMID=?";

    private String getAllLeaguesSQL = "select ID, COUNTRYID, LEAGUEID, LEAGUENAME from SCOREPREDICTION.LEAGUE";
    private String getAllTeamsSQL = "select ID, TEAMID, TEAMNAME, COUNTRYID from SCOREPREDICTION.TEAM";
    private String getAllPlayersSQL = "select ID, PLAYERNAME from SCOREPREDICTION.PLAYER";
    private String getAllSeasonsSQL = "select ID, TERM from SCOREPREDICTION.SEASON ORDER BY SEASONID DESC";
    private String getAllActiveSeasonsSQL = "select ID, TERM from SCOREPREDICTION.SEASON WHERE STATUS=? ORDER BY ID DESC";

    private String findTeamByCountryIdAndTeamNameSQL = "select ID from SCOREPREDICTION.TEAM WHERE (COUNTRYID=? AND TEAMNAME=?)";
    private String findTeamByTeamIdAndTeamNameSQL = "select ID from SCOREPREDICTION.TEAM WHERE (TEAMID=? AND TEAMNAME=?)";

    private String addNewCountrySQL = "insert into SCOREPREDICTION.COUNTRY(COUNTRYNAME) values(?)";
    private String addNewLeagueSQL = "insert into SCOREPREDICTION.LEAGUE(LEAGUEID,COUNTRYID,LEAGUENAME,FIXTURE,ISCUP,LIVESCORE,HISTORICALDATA,NUMBEROFMATCHES,LATESTMATCH) values(?,?,?,?,?,?,?,?,?)";
    private String addNewTeamSQL = "insert into SCOREPREDICTION.TEAM(TEAMID,TEAMNAME,COUNTRYID,STADIUM,HOMEPAGE,WIKI) values(?,?,?,?,?,?)";
    private String addNewTeamStandingSQL = "insert into SCOREPREDICTION.TEAMDETAIL(TEAMID,LEAGUEID,SEASONID,PLAYED,ATHOME,AWAY,WON,DRAW,LOST,SHOT,YELLOWCARD,REDCARD,GOALFOR,GOALAGAINST,GOALDIFFERENCE,POINT) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private String addNewPlayerSQL = "insert into SCOREPREDICTION.PLAYER(PLAYERID,PLAYERNAME,FIRSTNAME,LASTNAME,CURRENTTEAMID,LOANTO,PLAYERPOSITION,NATIONALITY,DATEOFBIRTH,DATEOFSIGNING,SIGNING) values(?,?,?,?,?,?,?,?,?,?,?)";
    private String addNewBasicPlayerSQL = "insert into SCOREPREDICTION.PLAYER(PLAYERNAME,FIRSTNAME,LASTNAME,CURRENTTEAMID) values(?,?,?,?)";
    private String addNewMatchSQL = "insert into SCOREPREDICTION.MATCH(MATCHID,LEAGUEID,HOMETEAMID,AWAYTEAMID,MATCHRESULT,MATCHSCORE,MATCHSTATUS,MATCHDATE,MATCHTIME,LOCATION,ROUND,GROUPID,SPECTRATORS,MATCHREFEREE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private String addNewMatchEventSQL = "insert into SCOREPREDICTION.MATCHEVENT(MATCHID,HOMEORAWAY,TEAMID,GOAL,HALFTIMEGOAL,SHOT,SHOTONTARGET,HALFTIMESHOT,HALFTIMESHOTONTARGET,CORNER,YELLOWCARD,REDCARD,COACH,FORMATION,GOALDETAIL,REDCARDDETAIL,YELLOWCARDDETAIL,GOALKEEPER,DEFENSEE,MIDFIELD,FORWARD,SUBSTITUTE,SUBDETAIL) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private String addNewMatchGoalSQL = "insert into SCOREPREDICTION.MATCHGOAL(MATCHEVENTID,PLAYERID,PLAYERNAME,GOALTIME,GOALTYPE) values(?,?,?,?,?)";


    public DBOperations(String driver, String url, String user, String pass) {
        this.JDBC_DRIVER = driver;
        this.DB_URL = url;
        this.DB_USERNAME = user;
        this.DB_PASSWORD = pass;
    }

    private void getConnection(){
        this.mysqlConnection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            mysqlConnection = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
        }catch (Exception ex){
            System.out.println("EXCEPTION occurred in DB connection:"+ex);
        }
    }

    private void closeConnection(){
        try{
            if(mysqlPreparedStatement!=null)
                mysqlPreparedStatement.close();
            if(mysqlResultSet!=null)
                mysqlResultSet.close();
        }catch(SQLException ex){
            System.out.println("SQL EXCEPTION occurred in closing statement and resultset: " + ex);
        }catch(Exception ex){
            System.out.println("EXCEPTION occurred in closing statement and resultset: " + ex);
        }
        finally{
            try {
                if(mysqlConnection!=null)
                    mysqlConnection.close();
            }catch(Exception ex){
                System.out.println("EXCEPTION occurred in closing connection: " + ex);
            }
        }

    }
    public int findCountryByName(String country){
        int countryID=0;
        try{
            getConnection();
            mysqlPreparedStatement = mysqlConnection.prepareStatement(findCountrySQL);
            mysqlPreparedStatement.setString(1,country);
            mysqlResultSet = mysqlPreparedStatement.executeQuery();
            if((mysqlResultSet.next())) {
                countryID = mysqlResultSet.getInt(1);
            }
        }catch(Exception ex){
            System.out.println("EXCEPTION occurred in findCountryByName: "+ex);
        }finally{
            closeConnection();
        }
        return countryID;
    }

    public int addCountryByName(String countryName){
        int countryID=0;
        try{
            getConnection();
            mysqlPreparedStatement = mysqlConnection.prepareStatement(addNewCountrySQL);
            mysqlPreparedStatement.setString(1,countryName);
            if(mysqlPreparedStatement.executeUpdate()>0){
                countryID= findCountryByName(countryName);
                //System.out.println("COUNTRY -> " + countryName + " is added to DB -> " + countryID);
            }
        }catch(Exception ex){
            System.out.println("EXCEPTION occurred in addCountryByName: "+ex);
        }finally{
            closeConnection();
        }
        return countryID;
    }

    public boolean findLeagueByCountryIdAndLeagueName(int countryID, String leagueName){
        boolean leagueFound=false;
        try{
            getConnection();
            mysqlPreparedStatement= mysqlConnection.prepareStatement(findLeagueByCountryIdAndLeagueNameSQL);
            mysqlPreparedStatement.setInt(1,countryID);
            mysqlPreparedStatement.setString(2,leagueName);
            mysqlResultSet = mysqlPreparedStatement.executeQuery();
            leagueFound=mysqlResultSet.next();
        }catch(Exception ex){
            System.out.println("EXCEPTION occurred in findLeagueByCountryIdAndLeagueName: "+ex);
        }finally{
            closeConnection();
        }
        return leagueFound;
    }
    public int findLeagueByLeagueName(String leagueName){
        int leagueID=0;
        try{
            getConnection();
            mysqlPreparedStatement= mysqlConnection.prepareStatement(findLeagueByLeagueNameSQL);
            mysqlPreparedStatement.setString(1,leagueName);
            mysqlResultSet = mysqlPreparedStatement.executeQuery();
            if(mysqlResultSet.next()){
                leagueID=mysqlResultSet.getInt(1);
            }
        }catch(Exception ex){
            System.out.println("EXCEPTION occurred in findLeagueByLeagueName: "+ex);
        }finally{
            closeConnection();
        }
        return leagueID;
    }
    public Map<Integer,LeagueStatement> getAllLeagues(){
        Map<Integer,LeagueStatement> allLeaguesOnDB = new HashMap<>();
        int lId;
        int countryID;
        int leagueID;
        String leagueName;
        LeagueStatement leagueStatement=null;
        try{
            getConnection();
            mysqlPreparedStatement= mysqlConnection.prepareStatement(getAllLeaguesSQL);
            mysqlResultSet = mysqlPreparedStatement.executeQuery();
            while(mysqlResultSet.next()){
                leagueStatement=new LeagueStatement();
                lId=mysqlResultSet.getInt(1);
                countryID=mysqlResultSet.getInt(2);
                leagueID=mysqlResultSet.getInt(3);
                leagueName=mysqlResultSet.getString(4);
                leagueStatement.setID(lId);
                leagueStatement.setCountryID(countryID);
                leagueStatement.setLeagueID(leagueID);
                leagueStatement.setLeagueName(leagueName);
                allLeaguesOnDB.put(lId,leagueStatement);
            }
        }catch(Exception ex){
            System.out.println("EXCEPTION occurred in getAllLeagues: "+ex);
        }finally{
            closeConnection();
        }
        return allLeaguesOnDB;
    }

    public List<SeasonStatement> getAllActiveSeasons(){
        List<SeasonStatement> allSeasonsOnDB = new ArrayList<>();
        int sId;
        String seasonTerm;
        int activeSession=1;
        SeasonStatement seasonStatement=null;
        try{
            getConnection();
            //System.out.println("Get All Seasons starting...");
            mysqlPreparedStatement= mysqlConnection.prepareStatement(getAllActiveSeasonsSQL);
            mysqlPreparedStatement.setInt(1,activeSession);
            mysqlResultSet = mysqlPreparedStatement.executeQuery();
            while(mysqlResultSet.next()){
                seasonStatement=new SeasonStatement();
                sId=mysqlResultSet.getInt(1);
                seasonTerm=mysqlResultSet.getString(2);
                seasonStatement.setID(sId);
                seasonStatement.setSeasonTerm(seasonTerm);
                //System.out.println("seasonID:"+seasonID);
                //System.out.println("seasonTerm:"+seasonTerm);
                allSeasonsOnDB.add(seasonStatement);
            }
        }catch(Exception ex){
            System.out.println("EXCEPTION occurred in getAllSeasons: "+ex);
        }finally{
            closeConnection();
        }
        return allSeasonsOnDB;
    }

    public boolean findTeam(TeamStatement teamStatement){
        boolean teamFound=false;
        String sqlStatement;
        int numericParameter;
        String alphaNumericParameter=teamStatement.getTeamName();

        switch (teamStatement.getCountryID()){
            case 0:
                sqlStatement=this.findTeamByTeamIdAndTeamNameSQL;
                numericParameter=teamStatement.getTeamID();
                break;
            default:
                sqlStatement=this.findTeamByCountryIdAndTeamNameSQL;
                numericParameter=teamStatement.getCountryID();
                break;
        }
        try{
            getConnection();
            mysqlPreparedStatement = mysqlConnection.prepareStatement(sqlStatement);
            mysqlPreparedStatement.setString(2, alphaNumericParameter);
            mysqlPreparedStatement.setInt(1,numericParameter);
            mysqlResultSet = mysqlPreparedStatement.executeQuery();
            teamFound=mysqlResultSet.next();
        }catch(Exception ex){
            System.out.println("EXCEPTION occurred in findTeam: "+ex);
        }finally{
            closeConnection();
        }
        return teamFound;
    }


    public void addLeague(League leagueItem, int countryID){
        int leagueID=0;
        String leagueName=null;
        int leagueFixtures=0;
        int leagueCup=0;
        int leagueLivescore=0;
        int leagueHistoricalData=0;
        int leagueNumberOfMatches=0;
        String leagueLatestMatchDate=null;
        Timestamp latestMatchSQL=null;
        java.util.Date latestMatchDATE=new java.util.Date();
        try{
            getConnection();
            leagueID=leagueItem.getId();
            leagueName=leagueItem.getName();
            leagueFixtures=leagueItem.getFixtures();
            leagueCup=leagueItem.getCup();
            leagueLivescore=leagueItem.getLivescore();
            leagueHistoricalData=leagueItem.getHistoricalData();
            leagueNumberOfMatches=leagueItem.getNumberOfMatches();
            leagueLatestMatchDate=leagueItem.getLatestMatch();
            SimpleDateFormat currentDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            mysqlPreparedStatement = mysqlConnection.prepareStatement(addNewLeagueSQL);
            mysqlPreparedStatement.setInt(1,leagueID);
            mysqlPreparedStatement.setInt(2,countryID);
            mysqlPreparedStatement.setString(3,leagueName);
            mysqlPreparedStatement.setInt(4,leagueFixtures);
            mysqlPreparedStatement.setInt(5,leagueCup);
            mysqlPreparedStatement.setInt(6,leagueLivescore);
            mysqlPreparedStatement.setInt(7,leagueHistoricalData);
            mysqlPreparedStatement.setInt(8,leagueNumberOfMatches);
            if(null!=leagueLatestMatchDate){
                try{
                    latestMatchDATE=currentDateFormat.parse(leagueLatestMatchDate);
                    latestMatchSQL= new Timestamp(latestMatchDATE.getTime());
                    mysqlPreparedStatement.setTimestamp(9,latestMatchSQL);
                }catch (Exception ex){
                    System.out.println("EXCEPTION occurred in parsing leagueLatestMatchDate on addLeague operation: "+ex);
                    mysqlPreparedStatement.setTimestamp(9,null);
                }
            }else{
                mysqlPreparedStatement.setTimestamp(9,null);
            }
            int executionResult=mysqlPreparedStatement.executeUpdate();
            //System.out.println("LEAGUE -> " + leagueName + " is added to DB -> " + executionResult);
        }catch(Exception ex){
            System.out.println("EXCEPTION occurred in addLeague: "+ex);
        }finally{
            closeConnection();
        }
    }

    //TEAM OPERATIONS
    public void addTeamByCountryId(Team teamItem, int countryID){
        int teamID=0;
        String teamName=null;
        String teamCountry=null;
        String teamStadium=null;
        String teamHomePage=null;
        String teamWiki=null;
        try{
            getConnection();
            teamID=teamItem.getId();
            teamName=teamItem.getName();
            teamStadium=teamItem.getStadium();
            teamHomePage=teamItem.getHomePageURL();
            teamWiki=teamItem.getWIKILink();
            mysqlPreparedStatement = mysqlConnection.prepareStatement(addNewTeamSQL);
            mysqlPreparedStatement.setInt(1,teamID);
            mysqlPreparedStatement.setString(2,teamName);
            mysqlPreparedStatement.setInt(3,countryID);
            mysqlPreparedStatement.setString(4,teamStadium);
            mysqlPreparedStatement.setString(5,teamHomePage);
            mysqlPreparedStatement.setString(6,teamWiki);
            int retVal = mysqlPreparedStatement.executeUpdate();
            //System.out.println("TEAM -> " + teamName + " is added to DB -> " + retVal);
        }catch(Exception ex){
            System.out.println("EXCEPTION occurred in addTeamByCountryId: "+ex);
        }finally{
            closeConnection();
        }
    }
    public void addTeamStanding(TeamStanding teamStandingItem, int lId, int seasonID){
        int teamID=0;
        int played=0;
        int atHome=0;
        int away=0;
        int won=0;
        int draw=0;
        int lost=0;
        int shot=0;
        int yellowCard=0;
        int redCard=0;
        int goalFor=0;
        int goalAgainst=0;
        int goalDifference=0;
        int point=0;
        try{
            getConnection();
            teamID=teamStandingItem.getId();
            played=teamStandingItem.getPlayed();
            atHome=teamStandingItem.getPlayedAtHome();
            away=teamStandingItem.getPlayedAway();
            won=teamStandingItem.getWon();
            draw=teamStandingItem.getDraw();
            lost=teamStandingItem.getLost();
            shot=teamStandingItem.getNumberOfShot();
            yellowCard=teamStandingItem.getYellowCard();
            redCard=teamStandingItem.getRedCard();
            goalFor=teamStandingItem.getGoalFor();
            goalAgainst=teamStandingItem.getGoalAgainst();
            goalDifference=teamStandingItem.getGoalDifference();
            point=teamStandingItem.getPoint();
            mysqlPreparedStatement = mysqlConnection.prepareStatement(addNewTeamStandingSQL);
            mysqlPreparedStatement.setInt(1,teamID);
            mysqlPreparedStatement.setInt(2,lId);
            mysqlPreparedStatement.setInt(3,seasonID);
            mysqlPreparedStatement.setInt(4,played);
            mysqlPreparedStatement.setInt(5,atHome);
            mysqlPreparedStatement.setInt(6,away);
            mysqlPreparedStatement.setInt(7,won);
            mysqlPreparedStatement.setInt(8,draw);
            mysqlPreparedStatement.setInt(9,lost);
            mysqlPreparedStatement.setInt(10,shot);
            mysqlPreparedStatement.setInt(11,yellowCard);
            mysqlPreparedStatement.setInt(12,redCard);
            mysqlPreparedStatement.setInt(13,goalFor);
            mysqlPreparedStatement.setInt(14,goalAgainst);
            mysqlPreparedStatement.setInt(15,goalDifference);
            mysqlPreparedStatement.setInt(16,point);
            int retVal=mysqlPreparedStatement.executeUpdate();
            //System.out.println("TEAM DETAIL for TeamID:" + teamID + " is added to DB -> " + retVal);
        }catch(Exception ex){
            System.out.println("EXCEPTION occurred in addTeamStanding: "+ex);
        }finally{
            closeConnection();
        }
    }
    public Map<Integer,TeamStatement> getAllTeams(){
        Map<Integer,TeamStatement> allTeamsOnDB = new HashMap<>();
        int id;
        int teamID;
        String teamNAME;
        int countryId;
        TeamStatement teamStatement=null;
        try{
            getConnection();
            //System.out.println("Get All Teams starting...");
            mysqlPreparedStatement= mysqlConnection.prepareStatement(getAllTeamsSQL);
            mysqlResultSet = mysqlPreparedStatement.executeQuery();
            while(mysqlResultSet.next()){
                teamStatement=new TeamStatement();
                id=mysqlResultSet.getInt(1);
                teamID=mysqlResultSet.getInt(2);
                teamNAME=mysqlResultSet.getString(3);
                countryId=mysqlResultSet.getInt(4);
                teamStatement.setID(id);
                teamStatement.setTeamID(teamID);
                teamStatement.setTeamName(teamNAME);
                teamStatement.setCountryID(countryId);
                allTeamsOnDB.put(teamID,teamStatement);
            }
        }catch(Exception ex){
            System.out.println("EXCEPTION occurred in getAllTeams: "+ex);
        }finally{
            closeConnection();
        }
        return allTeamsOnDB;
    }

    //PLAYER OPERATIONS
    public void addPlayer(Player playerItem, int teamID){
        int playerID=0;
        String playerName=null;
        String firstName=null;
        String lastName=null;
        String nickName=null;
        String[] playerNames=null;
        int totalNameSize=0;
        //int playerTeamID=teamID;
        int playerTeamID=0;
        int loanToID=0;
        int playerPosition;
        String nationality;
        String birthDate;
        String signingDate;
        String signing;
        Timestamp SQL;
        java.util.Date DATE=new java.util.Date();
        try{
            getConnection();
            playerID=playerItem.getId();
            playerName=playerItem.getName();
            if(null!=playerName){
                playerName=playerName.trim();
                firstName=playerName;
                playerNames = playerName.split(" ");
                totalNameSize= playerNames.length;
                if((totalNameSize>1)){
                    lastName=playerNames[totalNameSize-1];
                    firstName=playerName.substring(0,playerName.length()-lastName.length()).trim();
                }
            }

            //playerTeamID=playerItem.getTeamId()==0?teamID:playerItem.getTeamId();
            playerTeamID=playerItem.getTeamId();
            loanToID=playerItem.getLoanTo();
            playerPosition=resolvePosition(playerItem.getPosition());
            nationality=playerItem.getNationality();
            birthDate=playerItem.getDateOfBirth();
            signingDate=playerItem.getDateOfSigning();
            signing=playerItem.getSigning();
            SimpleDateFormat currentDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            if((playerItem.getTeamId()==0) && (playerItem.getLoanTo()!=teamID)){
                System.out.println("WARNING - CHECK THIS PLAYER (" + playerItem.toString()+")");
            }
            mysqlPreparedStatement = mysqlConnection.prepareStatement(addNewPlayerSQL);
            mysqlPreparedStatement.setInt(1,playerID);
            mysqlPreparedStatement.setString(2,playerName);
            mysqlPreparedStatement.setString(3,firstName);
            mysqlPreparedStatement.setString(4,lastName);
            mysqlPreparedStatement.setInt(5,playerTeamID);
            mysqlPreparedStatement.setInt(6,loanToID);
            mysqlPreparedStatement.setInt(7,playerPosition);
            mysqlPreparedStatement.setString(8,nationality);
            if(null!=birthDate){
                try{
                    DATE=currentDateFormat.parse(birthDate);
                    SQL= new Timestamp(DATE.getTime());
                    mysqlPreparedStatement.setTimestamp(9,SQL);
                }catch(Exception ex){
                    System.out.println("EXCEPTION occurred in parsing birthDate on addPlayer operation: "+ex);
                    mysqlPreparedStatement.setTimestamp(9,null);
                }
            }else{
                mysqlPreparedStatement.setTimestamp(9,null);
            }
            if(null!=signingDate){
                try{
                    DATE=currentDateFormat.parse(signingDate);
                    SQL= new Timestamp(DATE.getTime());
                    mysqlPreparedStatement.setTimestamp(10,SQL);
                }catch(Exception ex){
                    System.out.println("EXCEPTION occurred in parsing signingDate on addPlayer operation: "+ex);
                    mysqlPreparedStatement.setTimestamp(10,null);
                }
            }else{
                mysqlPreparedStatement.setTimestamp(10,null);
            }
            mysqlPreparedStatement.setString(11,signing);
            mysqlPreparedStatement.executeUpdate();
            //System.out.println("PLAYER for PlayerID:" + playerID + " is added to DB");
        }catch(Exception ex){
            System.out.println("HOWCOME EXCEPTION occurred in adding player(" + playerName+"/" + playerID + "/" + playerTeamID + "/" + loanToID +") detail: "+ex);
        }finally{
            closeConnection();
        }
    }
    //PLAYER OPERATIONS
    public int addBasicPlayer(Player playerItem, int teamID){
        String playerName=null;
        String firstName=null;
        String lastName=null;
        String nickName=null;
        String[] playerNames=null;
        int playerId = 0;
        int totalNameSize=0;
        //int playerTeamID=teamID;
        int playerTeamID=0;
        try{
            playerName=playerName.trim();
            firstName=playerName;
            playerNames = playerName.split(" ");
            totalNameSize= playerNames.length;
            if((totalNameSize>1)){
                lastName=playerNames[totalNameSize-1];
                firstName=playerName.substring(0,playerName.length()-lastName.length()).trim();
            }

            playerTeamID=playerItem.getTeamId();
            if((playerItem.getTeamId()==0) && (playerItem.getLoanTo()!=teamID)){
                System.out.println("WARNING - CHECK THIS PLAYER (" + playerItem.toString()+")");
            }
            mysqlPreparedStatement = mysqlConnection.prepareStatement(addNewBasicPlayerSQL);
            mysqlPreparedStatement.setString(1,playerName);
            mysqlPreparedStatement.setString(2,firstName);
            mysqlPreparedStatement.setString(3,lastName);
            mysqlPreparedStatement.setInt(4,playerTeamID);
            mysqlPreparedStatement.executeUpdate();
            Thread.sleep(50);
            mysqlPreparedStatement = mysqlConnection.prepareStatement(findPlayerByPlayerNameAndTeamIdSQL);
            mysqlPreparedStatement.setString(1,playerName);
            mysqlPreparedStatement.setInt(2,playerTeamID);
            mysqlResultSet = mysqlPreparedStatement.executeQuery();
            if((mysqlResultSet.next())) {
                playerId = mysqlResultSet.getInt(1);
            }
            //System.out.println("PLAYER for PlayerID:" + playerID + " is added to DB");
        }catch(Exception ex){
            System.out.println("HOWCOME EXCEPTION occurred in adding basic player(" + playerItem.toString() +") detail: "+ex);
        }finally{
            //closeConnection();
            return playerId;
        }
    }
    public boolean findPlayerByPlayerIdAndPlayerName(int playerID, String playerNAME){
        boolean found=false;
        try{
            getConnection();
            mysqlPreparedStatement= mysqlConnection.prepareStatement(findPlayerByPlayerIdAndPlayerNameSQL);
            mysqlPreparedStatement.setInt(1,playerID);
            mysqlPreparedStatement.setString(2,playerNAME);
            mysqlResultSet = mysqlPreparedStatement.executeQuery();
            found=mysqlResultSet.next();
        }catch(Exception ex){
            System.out.println("EXCEPTION occurred in findPlayerByPlayerIdAndPlayerName: "+ex);
        }finally{
            closeConnection();
        }
        return found;
    }
    public Map<Integer,PlayerStatement> getAllPlayers(){
        Map<Integer,PlayerStatement> allPlayersOnDB = new HashMap<>();
        int playerID;
        String playerNAME;
        PlayerStatement playerStatement=null;
        try{
            getConnection();
            //System.out.println("Get All Players starting...");
            mysqlPreparedStatement= mysqlConnection.prepareStatement(getAllPlayersSQL);
            mysqlResultSet = mysqlPreparedStatement.executeQuery();
            while(mysqlResultSet.next()){
                playerStatement=new PlayerStatement();
                playerID=mysqlResultSet.getInt(1);
                playerNAME=mysqlResultSet.getString(2);
                playerStatement.setPlayerID(playerID);
                playerStatement.setPlayerName(playerNAME);
                allPlayersOnDB.put(playerID,playerStatement);
            }
        }catch(Exception ex){
            System.out.println("EXCEPTION occurred in getAllPlayers: "+ex);
        }finally{
            closeConnection();
        }
        return allPlayersOnDB;
    }
    private static int resolvePosition(String position){
        int positionId=0;
        switch(position){
            case "GOALKEEPER":
                positionId=1;
                break;
            case "DEFENDER":
                positionId=2;
                break;
            case "MIDFIELDER":
                positionId=3;
                break;
            case "FORWARD":
                positionId=4;
                break;
            default:
                positionId=0;
                break;
        }
        return positionId;
    }

    //MATCH
    public int findMatchByMatchFixtureID(int matchFixtureID){
        int fixtureID=0;
        try{
            getConnection();
            mysqlPreparedStatement = mysqlConnection.prepareStatement(findMatchByFixtureIDSQL);
            mysqlPreparedStatement.setInt(1,matchFixtureID);
            mysqlResultSet = mysqlPreparedStatement.executeQuery();
            if((mysqlResultSet.next())) {
                fixtureID = mysqlResultSet.getInt(1);
            }
        }catch(Exception ex){
            System.out.println("EXCEPTION occurred in findMatchByMatchFixtureID: "+ex);
        }finally{
            closeConnection();
        }
        return fixtureID;
    }
    public void addMatch(Match matchItem, int lId, int sId){

        //MATCH SPECIFIC
        int home=1;
        int away=2;
        int halfTimeShot=0;
        int halfTimeShotOnTarget=0;
        int matchEventId=0;
        int matchId=matchItem.getId();
        String matchDate=matchItem.getDate();
        int matchFixtureId=matchItem.getFixtureMatchId();
        String matchLeague=matchItem.getLeague();
        String matchReferee=matchItem.getReferee();
        int matchRound=matchItem.getRound();
        int matchSpectrator=matchItem.getSpectator();
        String matchLocation=matchItem.getHomeTeam();
        int matchGroupId=matchItem.getGroupId();
        int matchResult=0;
        int matchStatus=1;
        int matchTime=90;

        //HOME
        String homeTeam=matchItem.getHomeTeam();
        int homeTeamId=matchItem.getHomeTeamId();
        int homeRedCard=matchItem.getHomeRedCard();
        String homeTeamRedCardDetail=matchItem.getHomeTeamRedCardDetail();
        int homeYellowCard=matchItem.getHomeYellowCard();
        String homeTeamYellowCardDetail=matchItem.getHomeTeamYellowCardDetail();
        int homeHalfTimeGoal=matchItem.getHalfTimeHomeGoal();
        int homeGoal=matchItem.getHomeGoal();
        String homeGoalDetail=matchItem.getHomeGoalDetail().trim();
        int homeCorner=matchItem.getHomeCorner();
        int homeShot=matchItem.getHomeShot();
        int homeShotOnTarget=matchItem.getHomeShotOnTarget();
        String homeLineupGoalkeeper=matchItem.getHomeLineupGoalkeeper();
        String homeLineupDefense=matchItem.getHomeLineupDefense();
        String homeLineupMidfield=matchItem.getHomeLineupMidfield();
        String homeLineupForward=matchItem.getHomeLineupForward();
        String homeLineupSubstitute=matchItem.getHomeLineupSubstitute();
        String homeLineupCoach=matchItem.getHomeLineupCoach();
        String homeSubDetail=matchItem.getHomeSubDetail();
        String homeTeamFormation=matchItem.getHomeTeamFormation();

        //AWAY
        String awayTeam=matchItem.getAwayTeam();
        int awayTeamId=matchItem.getAwayTeamId();
        int awayRedCard=matchItem.getAwayRedCard();
        String awayTeamRedCardDetail=matchItem.getAwayTeamRedCardDetail();
        int awayYellowCard=matchItem.getAwayYellowCard();
        String awayTeamYellowCardDetail=matchItem.getAwayTeamYellowCardDetail();
        int awayHalfTimeGoal=matchItem.getHalfTimeAwayGoal();
        int awayGoal=matchItem.getAwayGoal();
        String awayGoalDetail=matchItem.getAwayGoalDetail().trim();
        int awayCorner=matchItem.getAwayCorner();
        int awayShot=matchItem.getAwayShot();
        int awayShotOnTarget=matchItem.getAwayShotOnTarget();
        String awayLineupGoalkeeper=matchItem.getAwayLineupGoalkeeper();
        String awayLineupDefense=matchItem.getAwayLineupDefense();
        String awayLineupMidfield=matchItem.getAwayLineupMidfield();
        String awayLineupForward=matchItem.getAwayLineupForward();
        String awayLineupSubstitute=matchItem.getAwayLineupSubstitute();
        String awayLineupCoach=matchItem.getAwayLineupCoach();
        String awaySubDetail=matchItem.getAwaySubDetail();
        String awayTeamFormation=matchItem.getAwayTeamFormation();

        if(homeGoal>awayGoal){
            matchResult=1;
        }else{
            if(awayGoal>homeGoal){
                matchResult=2;
            }else{
                matchResult=0;
            }
        }

        String matchScore=String.valueOf(homeGoal)+"-"+String.valueOf(awayGoal);
        //System.out.println("Match Fixture Id:"+matchFixtureId + " - Match Id(From REST):"+matchId);
        try{
            getConnection();

            //MATCH
            mysqlPreparedStatement = mysqlConnection.prepareStatement(addNewMatchSQL);
            mysqlPreparedStatement.setInt(1,matchFixtureId);
            mysqlPreparedStatement.setInt(2,lId);
            mysqlPreparedStatement.setInt(3,homeTeamId);
            mysqlPreparedStatement.setInt(4,awayTeamId);
            mysqlPreparedStatement.setInt(5,matchResult);
            mysqlPreparedStatement.setString(6,matchScore);
            mysqlPreparedStatement.setInt(7,matchStatusOperation(matchDate));
            mysqlPreparedStatement.setTimestamp(8,dateOperation(matchDate));
            mysqlPreparedStatement.setInt(9,matchTime);
            mysqlPreparedStatement.setString(10,matchLocation);
            mysqlPreparedStatement.setInt(11,matchRound);
            mysqlPreparedStatement.setInt(12,matchGroupId);
            mysqlPreparedStatement.setInt(13,matchSpectrator);
            mysqlPreparedStatement.setString(14,matchReferee);
            mysqlPreparedStatement.executeUpdate();
            Thread.sleep(50);

            mysqlPreparedStatement = mysqlConnection.prepareStatement(findMatchIdSQL);
            mysqlPreparedStatement.setInt(1,matchFixtureId);
            mysqlPreparedStatement.setInt(2,lId);
            mysqlResultSet = mysqlPreparedStatement.executeQuery();
            if((mysqlResultSet.next())) {
                matchId = mysqlResultSet.getInt(1);
            }
            //System.out.println("Match Id(From Match Table):"+matchId);

            Thread.sleep(50);

            //HOME MATCHEVENT
            mysqlPreparedStatement = mysqlConnection.prepareStatement(addNewMatchEventSQL);
            mysqlPreparedStatement.setInt(1,matchId);
            mysqlPreparedStatement.setInt(2,home);
            mysqlPreparedStatement.setInt(3,homeTeamId);
            mysqlPreparedStatement.setInt(4,homeGoal);
            mysqlPreparedStatement.setInt(5,homeHalfTimeGoal);
            mysqlPreparedStatement.setInt(6,homeShot);
            mysqlPreparedStatement.setInt(7,homeShotOnTarget);
            mysqlPreparedStatement.setInt(8,halfTimeShot);
            mysqlPreparedStatement.setInt(9,halfTimeShotOnTarget);
            mysqlPreparedStatement.setInt(10,homeCorner);
            mysqlPreparedStatement.setInt(11,homeYellowCard);
            mysqlPreparedStatement.setInt(12,homeRedCard);
            mysqlPreparedStatement.setString(13,homeLineupCoach);
            mysqlPreparedStatement.setString(14,homeTeamFormation);
            mysqlPreparedStatement.setString(15,homeGoalDetail);
            mysqlPreparedStatement.setString(16,homeTeamRedCardDetail);
            mysqlPreparedStatement.setString(17,homeTeamYellowCardDetail);
            mysqlPreparedStatement.setString(18,homeLineupGoalkeeper);
            mysqlPreparedStatement.setString(19,homeLineupDefense);
            mysqlPreparedStatement.setString(20,homeLineupMidfield);
            mysqlPreparedStatement.setString(21,homeLineupForward);
            mysqlPreparedStatement.setString(22,homeLineupSubstitute);
            mysqlPreparedStatement.setString(23,homeSubDetail);
            mysqlPreparedStatement.executeUpdate();
            matchEventId=0;
            Thread.sleep(50);
            mysqlPreparedStatement = mysqlConnection.prepareStatement(findMatchEventIdIdSQL);
            mysqlPreparedStatement.setInt(1,matchId);
            mysqlPreparedStatement.setInt(2,homeTeamId);
            mysqlResultSet = mysqlPreparedStatement.executeQuery();
            if((mysqlResultSet.next())) {
                matchEventId = mysqlResultSet.getInt(1);
            }
            //System.out.println("Match Event Id (From MatchEvent Table): "+matchEventId + " for matchId:"+matchId+ " and teamId:"+homeTeamId);
            Thread.sleep(50);

            if((homeGoal>0)) {
                matchGoalOperation(homeGoalDetail, homeTeamId, sId, matchFixtureId, matchEventId);
            }

            //AWAY MATCHEVENT
            mysqlPreparedStatement = mysqlConnection.prepareStatement(addNewMatchEventSQL);
            mysqlPreparedStatement.setInt(1,matchId);
            mysqlPreparedStatement.setInt(2,away);
            mysqlPreparedStatement.setInt(3,awayTeamId);
            mysqlPreparedStatement.setInt(4,awayGoal);
            mysqlPreparedStatement.setInt(5,awayHalfTimeGoal);
            mysqlPreparedStatement.setInt(6,awayShot);
            mysqlPreparedStatement.setInt(7,awayShotOnTarget);
            mysqlPreparedStatement.setInt(8,halfTimeShot);
            mysqlPreparedStatement.setInt(9,halfTimeShotOnTarget);
            mysqlPreparedStatement.setInt(10,awayCorner);
            mysqlPreparedStatement.setInt(11,awayYellowCard);
            mysqlPreparedStatement.setInt(12,awayRedCard);
            mysqlPreparedStatement.setString(13,awayLineupCoach);
            mysqlPreparedStatement.setString(14,awayTeamFormation);
            mysqlPreparedStatement.setString(15,awayGoalDetail);
            mysqlPreparedStatement.setString(16,awayTeamRedCardDetail);
            mysqlPreparedStatement.setString(17,awayTeamYellowCardDetail);
            mysqlPreparedStatement.setString(18,awayLineupGoalkeeper);
            mysqlPreparedStatement.setString(19,awayLineupDefense);
            mysqlPreparedStatement.setString(20,awayLineupMidfield);
            mysqlPreparedStatement.setString(21,awayLineupForward);
            mysqlPreparedStatement.setString(22,awayLineupSubstitute);
            mysqlPreparedStatement.setString(23,awaySubDetail);
            mysqlPreparedStatement.executeUpdate();
            matchEventId=0;
            Thread.sleep(50);
            mysqlPreparedStatement = mysqlConnection.prepareStatement(findMatchEventIdIdSQL);
            mysqlPreparedStatement.setInt(1,matchId);
            mysqlPreparedStatement.setInt(2,awayTeamId);
            mysqlResultSet = mysqlPreparedStatement.executeQuery();
            if((mysqlResultSet.next())) {
                matchEventId = mysqlResultSet.getInt(1);
            }
            //System.out.println("Match Event Id (From MatchEvent Table): "+matchEventId + " for matchId:"+matchId+ " and teamId:"+awayTeamId);
            Thread.sleep(50);

            if(awayGoal>0){
                matchGoalOperation(awayGoalDetail, awayTeamId, sId, matchFixtureId, matchEventId);
            }
        }catch(Exception ex){
            System.out.println();
            System.out.println("EXCEPTION OCCURRED IN MATCH OR MATCH EVENT: "+ex);
            System.out.println(matchItem.toString());
            System.out.println();

        }finally{
            closeConnection();
        }

    }

    private void matchGoalOperation(String goalDetail, int teamId, int sessionId, int matchFixtureId, int matchEventId){
        String[] goalOwners;
        String[] ownerAndTimeArray;
        String ownerAndTime;
        String owner;
        String[] ownerArray;
        String ownerFirst;
        String ownerLast;
        String time;
        int playerId = 0;
        int goalType = 1;
        int recordCount=0;
        if ((null != goalDetail) && (!goalDetail.isEmpty())) {
            goalOwners = goalDetail.split(";");
            for (int x = 0; x < goalOwners.length; x++) {
                ownerAndTime = goalOwners[x];
                ownerAndTimeArray = ownerAndTime.split(":");
                time = ownerAndTimeArray[0].replace("'", "").trim();
                recordCount=0;
                if (ownerAndTimeArray[1].indexOf("PENALTY") > 0) {
                    owner = ownerAndTimeArray[1].replace("PENALTY", "").trim();
                    goalType = 2;
                } else {
                    owner = ownerAndTimeArray[1].trim();
                }
                if((null!=owner) && (!owner.isEmpty()) && (owner.length()>3)){
                    if (!owner.startsWith("OWN")) {
                        try{
                            mysqlPreparedStatement = mysqlConnection.prepareStatement(findPlayerByPlayerNameAndTeamIdSQL);
                            mysqlPreparedStatement.setString(1, '%' + owner + '%');
                            mysqlPreparedStatement.setInt(2, teamId);
                            mysqlPreparedStatement.setInt(3, teamId);
                            mysqlResultSet = mysqlPreparedStatement.executeQuery();
                            if((mysqlResultSet.next())){
                                playerId = mysqlResultSet.getInt(1);
                            }else{
                                ownerFirst=owner;
                                ownerLast=owner;
                                ownerArray = owner.split(" ");
                                int totalNameSize= ownerArray.length;
                                if((totalNameSize>1)){
                                    ownerLast=ownerArray[totalNameSize-1];
                                    ownerFirst=owner.substring(0,owner.length()-ownerLast.length()).trim();
                                }
                                mysqlPreparedStatement = mysqlConnection.prepareStatement(findPlayerByPlayerNamesAndTeamIdSQL);
                                mysqlPreparedStatement.setString(1, '%' + ownerFirst + '%');
                                mysqlPreparedStatement.setString(2, '%' + ownerLast + '%');
                                mysqlPreparedStatement.setInt(3, teamId);
                                mysqlPreparedStatement.setInt(4, teamId);
                                mysqlResultSet = mysqlPreparedStatement.executeQuery();
                                while(mysqlResultSet.next()){
                                    recordCount++;
                                    playerId = mysqlResultSet.getInt(1);
                                }
                                if (recordCount>1) {
                                    //todo bu durumda birden fazla adam varsa onu bulmak için bir şeyler yapılmalı
                                    System.out.println("WARNING THERE ARE MORE THAN ONE PLAYER HAS SIMILAR NAME: " + owner + " - first:" + ownerFirst + " - last:" + ownerLast + " - IN TEAM ID: " + teamId);
                                }
                                recordCount=0;
                                if(playerId==0){
                                    mysqlPreparedStatement = mysqlConnection.prepareStatement(findPlayerByPlayerNameSQL);
                                    mysqlPreparedStatement.setString(1, '%' + owner + '%');
                                    mysqlResultSet = mysqlPreparedStatement.executeQuery();
                                    while ((mysqlResultSet.next())) {
                                        recordCount++;
                                        playerId = mysqlResultSet.getInt(1);
                                    }
                                    if(recordCount>0){
                                        if (recordCount>1) {
                                            System.out.println("WARNING THERE ARE MORE THAN ONE PLAYER HAS SIMILAR NAME: " + owner);
                                        }else{
                                            if(sessionId==currentSessionID){
                                                mysqlPreparedStatement = mysqlConnection.prepareStatement(updatePlayerTeamByPlayerIdQL);
                                                mysqlPreparedStatement.setInt(1, teamId);
                                                mysqlPreparedStatement.setInt(2, playerId);
                                                mysqlPreparedStatement.executeUpdate();
                                                System.out.println("RECENTLY *UPDATED PLAYER: " + owner + " - TeamID:" + teamId);
                                                Thread.sleep(50);
                                            }
                                        }
                                    }else{
                                        if(sessionId==currentSessionID){
                                            Player player = new Player();
                                            player.setName(owner);
                                            player.setTeamId(teamId);
                                            playerId=addBasicPlayer(player,teamId);
                                            System.out.println("RECENTLY *ADDED PLAYER: " + owner + " - PlayerId:" + playerId + " - TeamID:" + teamId);
                                        }else{
                                            System.out.println("PLAYER NOT FOUND: " + owner + " - TeamID: " + teamId + " - matchFixtureId:"+matchFixtureId);
                                        }
                                    }
                                }
                            }

                            mysqlPreparedStatement = mysqlConnection.prepareStatement(addNewMatchGoalSQL);
                            mysqlPreparedStatement.setInt(1, matchEventId);
                            mysqlPreparedStatement.setInt(2, playerId);
                            mysqlPreparedStatement.setString(3, owner);
                            mysqlPreparedStatement.setInt(4, Integer.parseInt(time));
                            mysqlPreparedStatement.setInt(5, goalType);
                            mysqlPreparedStatement.executeUpdate();
                            //System.out.println("Goal Detail ADDED");
                            Thread.sleep(50);


                        }catch (Exception ex){
                            System.out.println();
                            System.out.println("EXCEPTION OCCURRED IN MATCH GOAL OPERATION: "+ex);
                            System.out.println(goalDetail + " & " + teamId + " & " + sessionId + " & " + matchFixtureId + " & " + matchEventId);
                            System.out.println();
                        }

                    }
                }else{
                    System.out.println("---stupid owner: " + owner);
                }

            }
        }

    }


    private static Timestamp dateOperation(String matchDate){
        Timestamp SQL;
        java.util.Date DATE=new java.util.Date();
        SimpleDateFormat currentDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        if(null!=matchDate) {
            try {
                DATE = currentDateFormat.parse(matchDate);
                return new Timestamp(DATE.getTime());
            } catch (Exception ex) {
                System.out.println("EXCEPTION occurred in parsing date: " + ex);
            }
        }
        return null;
    }
    private static int matchStatusOperation(String matchDate){
        Timestamp SQL;
        java.util.Date DATE=new java.util.Date();
        java.util.Date DATECURRENT=new java.util.Date();
        SimpleDateFormat currentDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        if(null!=matchDate) {
            try {
                DATE = currentDateFormat.parse(matchDate);
                if(DATECURRENT.after(DATE)){
                    return 1;
                }else{
                    return 3;
                }

            } catch (Exception ex) {
                System.out.println("EXCEPTION occurred in parsing date: " + ex);
            }
        }
        return 0;
    }


}
