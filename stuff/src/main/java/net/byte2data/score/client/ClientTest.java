package net.byte2data.score.client;

import net.byte2data.score.collectors.FetchAllLeagues;
import net.byte2data.score.collectors.FetchAllTeams;
import net.byte2data.score.collectors.FetchAllTeamsByLeagueAndSeason;
import net.byte2data.score.item.League;
import net.byte2data.score.item.Team;

import java.util.*;

/**
 * Created by barisci on 06.04.2016.
 */
public class ClientTest {

    public static void main(String... args) throws Exception{
        String apikey="XQKNYORREBXFHUDQJWDRWHDEANKCIUIVPJNLJHBSQUYEIKWSLH";

        String urlAllLeagues="http://www.xmlsoccer.com/FootballData.asmx/GetAllLeagues";
        String urlAllTeams="http://www.xmlsoccer.com/FootballData.asmx/GetAllTeams";
        String urlLeagueStandinsBySeason="http://www.xmlsoccer.com/FootballData.asmx/GetLeagueStandingsBySeason";
        String urlPayersByTeam="http://www.xmlsoccer.com/FootballData.asmx/GetPlayersByTeam";
        String urAllTeamsByLeagueAndSeason="http://www.xmlsoccer.com/FootballData.asmx/GetAllTeamsByLeagueAndSeason";
        String urAllMatchesByLeagueAndSeason="http://www.xmlsoccer.com/FootballData.asmx/GetFixturesByLeagueAndSeason";


        System.out.println("Fetch All Leagues STARTING");
        FetchAllLeagues fetchAllLeagues = new FetchAllLeagues(urlAllLeagues,apikey);
        Map<Integer,League> leagueMap=fetchAllLeagues.post();
        if((null!=leagueMap)&&(!leagueMap.isEmpty())){
            for(Map.Entry<Integer,League> leagueMapEntry : leagueMap.entrySet()){
                System.out.println(leagueMapEntry.getValue());
            }
        }
        System.out.println("****");

        System.out.println("Fetch All Teams STARTING");
        FetchAllTeams fetchAllTeams=new FetchAllTeams(urlAllTeams,apikey);
        Map<Integer,Team> teamMap=fetchAllTeams.post();
        if((null!=teamMap)&&(!teamMap.isEmpty())){
            for(Map.Entry<Integer,Team> teamMapEntry : teamMap.entrySet()){
                System.out.println(teamMapEntry.getValue());
            }
        }
        System.out.println("****");

        /*System.out.println("Fetch League Standings By Season STARTING");
        FetchLeagueStandingsBySeason fetchLeagueStandingsBySeason=new FetchLeagueStandingsBySeason(urlLeagueStandinsBySeason,apikey);
        Map<Integer,TeamStanding> teamStandingMap=fetchLeagueStandingsBySeason.post();
        if((null!=teamStandingMap)&&(!teamStandingMap.isEmpty())){
            for(Map.Entry<Integer,TeamStanding> teamStandingEntry : teamStandingMap.entrySet()){
                System.out.println(teamStandingEntry.getValue());
            }
        }
        System.out.println("****");*/

        /*
        System.out.println("Fetch Players By Team STARTING");
        FetchPlayersByTeam fetchPlayersByTeam=new FetchPlayersByTeam(urlPayersByTeam,apikey);
        Map<Integer,Player> playerMap=fetchPlayersByTeam.post();
        if((null!=playerMap)&&(!playerMap.isEmpty())){
            for(Map.Entry<Integer,Player> playerEntry : playerMap.entrySet()){
                System.out.println(playerEntry.getValue());
            }
        }
        System.out.println("****");
        */

        System.out.println("Fetch All Teams By League And Season STARTING");
        FetchAllTeamsByLeagueAndSeason fetchAllTeamsByLeagueAndSeason = new FetchAllTeamsByLeagueAndSeason(urAllTeamsByLeagueAndSeason,apikey);
        Map<Integer,Team> teamByLeagueAndSeasonMap=fetchAllTeamsByLeagueAndSeason.post();
        if((null!=teamByLeagueAndSeasonMap)&&(!teamByLeagueAndSeasonMap.isEmpty())){
            for(Map.Entry<Integer,Team> teamByLeagueAndSeasonEntry : teamByLeagueAndSeasonMap.entrySet()){
                System.out.println(teamByLeagueAndSeasonEntry.getValue());
            }
        }
        System.out.println("****");

        /*System.out.println("Fetch Matches By League And Season STARTING");
        FetchMatchesByLeagueAndSeason fetchMatchesByLeagueAndSeason = new FetchMatchesByLeagueAndSeason(urAllMatchesByLeagueAndSeason,apikey);
        Map<Integer,Match> matchByLeagueAndSeasonMap=fetchMatchesByLeagueAndSeason.post();
        if((null!=matchByLeagueAndSeasonMap)&&(!matchByLeagueAndSeasonMap.isEmpty())){
            for(Map.Entry<Integer,Match> matchByLeagueAndSeasonEntry : matchByLeagueAndSeasonMap.entrySet()){
                System.out.println(matchByLeagueAndSeasonEntry.getValue());
            }
        }
        System.out.println("****");*/

    }

}
