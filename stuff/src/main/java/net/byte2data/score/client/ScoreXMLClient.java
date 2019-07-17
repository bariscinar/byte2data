package net.byte2data.score.client;

import net.byte2data.score.collectors.*;
import net.byte2data.score.db.*;
import net.byte2data.score.item.*;

import java.net.URISyntaxException;
import java.util.*;

/**
 * Created by barisci on x1/25/16.
 */
public class ScoreXMLClient {

    private static final int CURRENT_SEASONID = 17;

    private static final String JDBC_DRIVER = "com.nysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://127.0.0.x1:3306/SCOREPREDICTION";
    //private static final String DB_URL = "jdbc:mysql://10.34.134.53:3306/SCOREPREDICTION";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "1306950049";

    private static final String API_KEY = "XQKNYORREBXFHUDQJWDRWHDEANKCIUIVPJNLJHBSQUYEIKWSLH";

    private static final String ALL_LEAGUES = "http://www.xmlsoccer.com/FootballData.asmx/GetAllLeagues";
    private static final String ALL_TEAMS = "http://www.xmlsoccer.com/FootballData.asmx/GetAllTeams";
    private static final String PLAYERS_BY_TEAM = "http://www.xmlsoccer.com/FootballData.asmx/GetPlayersByTeam";
    private static final String LEAGUE_STANDINGS = "http://www.xmlsoccer.com/FootballData.asmx/GetLeagueStandingsBySeason";
    private static final String MATCHES_BY_LEAGUE_AND_SEASON = "http://www.xmlsoccer.com/FootballData.asmx/GetHistoricMatchesByLeagueAndSeason";

    private static final String FIXTURES_BY_LEAGUE_AND_SEASON = "http://www.xmlsoccer.com/FootballData.asmx/GetFixturesByLeagueAndSeason";

    public static void main (String... args) throws URISyntaxException, InterruptedException {


        System.out.println("FETCHING COUNTRIES AND LEAGUES ARE STARTING...");
        try{
            FetchAllLeagues fetchAllLeagues = new FetchAllLeagues(ALL_LEAGUES,API_KEY);
            Map<Integer,League> leagueMap=fetchAllLeagues.post();
            LeagueDBOperation leagueDBOperation = new LeagueDBOperation(JDBC_DRIVER,DB_URL,DB_USERNAME,DB_PASSWORD);
            League leagueItem;
            if((null!=leagueMap)&&(!leagueMap.isEmpty())){
                for(Map.Entry<Integer,League> mapEntry : leagueMap.entrySet()){
                    leagueItem=mapEntry.getValue();
                    leagueDBOperation.checkLeague(leagueItem);
                }
            }else{
                System.out.println("LEAGUE MAP is EMPTY!");
            }
        }catch (Exception ex){
            System.out.println("EXCEPTION IN LEAGUE OPERATION: " + ex);
        }
        System.out.println("FETCHING COUNTRIES AND LEAGUES DONE...");
        System.out.println("WAITING ONE MINUTE");
        System.out.println("\n");
        Thread.sleep(60000);


        System.out.println("FETCHING TEAMS IS STARTING...");
        try{
            FetchAllTeams fetchAllTeams = new FetchAllTeams(ALL_TEAMS,API_KEY);
            Map<Integer,Team> teamMap=fetchAllTeams.post();
            TeamDBOperation teamDBOperation = new TeamDBOperation(JDBC_DRIVER,DB_URL,DB_USERNAME,DB_PASSWORD);
            Team teamItem;
            if((null!=teamMap)&&(!teamMap.isEmpty())){
                for(Map.Entry<Integer,Team> mapEntry : teamMap.entrySet()){
                    teamItem=mapEntry.getValue();
                    teamDBOperation.checkTeam(teamItem);
                }
            }else{
                System.out.println("TEAM MAP is EMPTY!");
            }
        }catch (Exception ex){
            System.out.println("EXCEPTION IN TEAM OPERATION: " + ex);
        }
        System.out.println("FETCHING TEAMS IS DONE...");
        System.out.println("WAITING ONE MINUTE");
        System.out.println("\n");
        Thread.sleep(60000);


        System.out.println("FETCHING PLAYERS BY TEAM IS STARTING....");
        TeamStatement teamStatement;
        Player player;
        FetchPlayersByTeam fetchPlayersByTeam;
        Map<Integer,Player> playerMap;
        int tId;
        int teamId;
        String teamName;
        int countryId=0;
        Map<Integer,TeamStatement> mapTeamOnDB;
        TeamDBOperation teamDBOperation=new TeamDBOperation(JDBC_DRIVER,DB_URL,DB_USERNAME,DB_PASSWORD);
        PlayerDBOperation playerDBOperation=new PlayerDBOperation(JDBC_DRIVER,DB_URL,DB_USERNAME,DB_PASSWORD);
        mapTeamOnDB=teamDBOperation.getAllTeams();
        if((null!=mapTeamOnDB)&&(!mapTeamOnDB.isEmpty())){
            for(Map.Entry<Integer,TeamStatement> mapTeamEntry : mapTeamOnDB.entrySet()){
                teamStatement=mapTeamEntry.getValue();
                tId=teamStatement.getID();
                teamId=teamStatement.getTeamID();
                teamName=teamStatement.getTeamName();
                countryId=teamStatement.getCountryID();
                //System.out.println("PLAYERS FETCHING FOR COUNTRYID/ID/TEAMID/TEAMNAME->"+countryId+"/"+tId+"/"+teamId+"/"+teamName);
                //System.out.println("-----------");
                try{
                    fetchPlayersByTeam=new FetchPlayersByTeam(PLAYERS_BY_TEAM,API_KEY,teamId);
                    playerMap=fetchPlayersByTeam.post();
                    if((null!=playerMap)&&(!playerMap.isEmpty())){
                        for(Map.Entry<Integer,Player> playerEntry : playerMap.entrySet()){
                            player=playerEntry.getValue();
                            playerDBOperation.checkPlayer(player,teamId);
                            //System.out.println(playerEntry.getValue());
                        }
                    }
                }catch(Exception ex){
                    System.out.println("Exception occurred in FetchPlayersByTeam Operation: "+ex);
                }
                //System.out.println("-----------");
                //System.out.println("\n");
            }
        }else{
            System.out.println("TEAMS ON DB IS EMPTY!!");
        }
        System.out.println("FETCHING PLAYERS BY TEAM IS DONE...");
        System.out.println("WAITING 10 SECS");
        System.out.println("\n");
        Thread.sleep(10000);



        System.out.println("FETCHING TEAMSTANDING IS STARTING....");
        Map<Integer,LeagueStatement> mapLeagueOnDB=new HashMap<>();
        List<SeasonStatement> listSeasonOnDB=new ArrayList<>();
        LeagueDBOperation leagueDBOperation=new LeagueDBOperation(JDBC_DRIVER,DB_URL,DB_USERNAME,DB_PASSWORD);
        SeasonDBOperation seasonDBOperation=new SeasonDBOperation(JDBC_DRIVER,DB_URL,DB_USERNAME,DB_PASSWORD);
        MatchDBOperation matchDBOperation = new MatchDBOperation(JDBC_DRIVER, DB_URL, DB_USERNAME, DB_PASSWORD);
        LeagueStandingsDBOperation leagueStandingsDBOperation=new LeagueStandingsDBOperation(JDBC_DRIVER,DB_URL,DB_USERNAME,DB_PASSWORD);
        mapLeagueOnDB=leagueDBOperation.getAllLeagues();
        listSeasonOnDB=seasonDBOperation.getAllSeasons();

        int lId;
        int countryID;
        int leagueID;
        String leagueName;

        int sId;
        String seasonTerm;

        FetchLeagueStandingsBySeason fetchLeagueStandingsBySeason=null;
        FetchMatchesByLeagueAndSeason fetchMatchesByLeagueAndSeason = null;

        Map<Integer,TeamStanding> teamStandingMap=null;
        Map<Integer, Match> matchMap=null;
        TeamStanding teamStandingItem=null;

        LeagueStatement leagueStatement=null;
        Match matchItem=null;

        if((null!=mapLeagueOnDB)&&(!mapLeagueOnDB.isEmpty())){
            Collections.sort(listSeasonOnDB);
            for(SeasonStatement ses : listSeasonOnDB){
                sId = ses.getID();
                seasonTerm = ses.getSeasonTerm();
                for(Map.Entry<Integer,LeagueStatement> mapLeagueEntry : mapLeagueOnDB.entrySet()){
                    leagueStatement=mapLeagueEntry.getValue();
                    lId=leagueStatement.getID();
                    leagueName=leagueStatement.getLeagueName();
                    leagueID=leagueStatement.getLeagueID();
                    System.out.println("");
                    System.out.println("");
                    //System.out.println("TEAM STANDING FOR ID" + lId + " - leagueID:" + leagueID + " - leagueName:" + leagueName + " - sId:" + sId + " - SeasonTerm:" + seasonTerm + " IS STARTING");
                    //System.out.println("-----------");
                    try{
                        fetchLeagueStandingsBySeason=new FetchLeagueStandingsBySeason(LEAGUE_STANDINGS,API_KEY,leagueName,seasonTerm);
                        teamStandingMap=fetchLeagueStandingsBySeason.post();
                        if((null!=teamStandingMap)&&(!teamStandingMap.isEmpty())){
                            for(Map.Entry<Integer,TeamStanding> mapEntry : teamStandingMap.entrySet()){
                                teamStandingItem=mapEntry.getValue();
                                leagueStandingsDBOperation.checkTeamStanting(teamStandingItem, lId, sId);
                            }
                        }else{
                            System.out.println("team standing map is empty!");
                        }
                        //System.out.println("TEAM STANDING FOR ID/LeagueID/LeagueName->"+lId+"/"+leagueID+"/"+leagueName+ " - SeasonID/SeasonTerm->"+sId+"/"+seasonTerm + " IS DONE");
                        //System.out.println("-----------");
                        //System.out.println("\n");
                        //Thread.sleep(5000);

                        System.out.println("MATCHES FOR ID/LeagueID/LeagueName->"+lId+"/"+leagueID+"/"+leagueName+ " - SeasonID/SeasonTerm->"+sId+"/"+seasonTerm + " IS STARTING");
                        System.out.println("-----------");
                        //if(seasonID==CURRENT_SEASONID){
                            //fetchMatchesByLeagueAndSeason = new FetchMatchesByLeagueAndSeason(FIXTURES_BY_LEAGUE_AND_SEASON, API_KEY, leagueName, seasonTerm);
                        //}else{
                            fetchMatchesByLeagueAndSeason = new FetchMatchesByLeagueAndSeason(MATCHES_BY_LEAGUE_AND_SEASON, API_KEY, leagueName, seasonTerm);
                        //}

                        matchMap = fetchMatchesByLeagueAndSeason.post();
                        if ((null != matchMap) && (!matchMap.isEmpty())) {
                            for (Map.Entry<Integer, Match> mapEntry : matchMap.entrySet()) {
                                matchItem = mapEntry.getValue();
                                int matchID = matchItem.getId();
                                int matchFixtureID = matchItem.getFixtureMatchId();
                                int homeTeamId = matchItem.getHomeTeamId();
                                int awayTeamId = matchItem.getAwayTeamId();
                                //TODO PLEASE CHECK THAT THIS PART WILL BE NEVER TRIGGERED!
                                if ((matchID <= 0) || (matchFixtureID <= 0) || (homeTeamId <= 0) || (awayTeamId <= 0)) {
                                    System.out.println("CHECK -> MATCH is GARBAGE -> " + matchItem.toString());
                                } else {
                                    matchDBOperation.checkMatch(matchItem,sId);
                                }
                            }
                        } else {
                            System.out.println("MATCH map is empty!");
                        }
                        //System.out.println("MATCHES FOR ID/LeagueID/LeagueName->"+lId+"/"+leagueID+"/"+leagueName+ " - SeasonID/SeasonTerm->"+sId+"/"+seasonTerm + " IS DONE");
                        //System.out.println("-----------");
                        //System.out.println("\n");

                    }catch (Exception ex){
                        System.out.println("exception: " + ex);
                    }
                    System.out.println("-----------");
                    System.out.println("Waiting 10 secs!!");
                    System.out.println("\n");
                    Thread.sleep(10000);
                }

                System.out.println("Waiting 10 minutes!!");
                System.out.println("\n");
                Thread.sleep(600000);
            }

        }else{
            System.out.println("LEAGUES ON DB IS EMPTY!!");
        }
        System.out.println("FETCHING TEAMSTANDING IS DONE...");
        System.out.println("\n");

    }
}
