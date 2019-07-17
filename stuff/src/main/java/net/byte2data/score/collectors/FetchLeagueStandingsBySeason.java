package net.byte2data.score.collectors;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import net.byte2data.score.cover.LeagueStanding;
import net.byte2data.score.item.TeamStanding;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by barisci on 05.04.2016.
 */
public class FetchLeagueStandingsBySeason {

    private static URI URL;
    private static String API_KEY;
    private static String LEAGUE_NAME;
    private static String SEASON;

    private Map<Integer,TeamStanding> leagueStandingMap;

    private Client client;
    private WebResource webResource;
    private ClientResponse clientResponse;
    private MultivaluedMap<String, String> formData = null;

    public FetchLeagueStandingsBySeason(String url, String apiKey, String leagueName,String season) throws URISyntaxException{
        URL = new URI(url);
        API_KEY = apiKey;
        LEAGUE_NAME=leagueName;
        SEASON=season;
        leagueStandingMap = new HashMap<>();
    }

    private void mapItemPut(TeamStanding teamTeamStanding){
        TeamStanding putResult = null;
        if(leagueStandingMap.containsKey(teamTeamStanding.getId())){
            if(teamTeamStanding.getPlayed()>leagueStandingMap.get(teamTeamStanding.getId()).getPlayed()){
                putResult = leagueStandingMap.put(teamTeamStanding.getId(), teamTeamStanding);
                System.out.println("CHECK -> TEAMSTANDING is already EXISTED in the MAP -> " + putResult.getId() + "-" + putResult.getTeam() + " is REPLACED with new->" +  teamTeamStanding.getId() + "-"  + teamTeamStanding.getTeam());
            }else{
                System.out.println("CHECK DUPLICATE TEAMSTANDING ENTRY ->" + teamTeamStanding.toString());
            }
        }else{
            leagueStandingMap.put(teamTeamStanding.getId(), teamTeamStanding);
            //System.out.println("TEAMSTANDING -> " + teamTeamStanding.getId() + "-" + teamTeamStanding.getTeam() + " is ADDED to MAP");
        }

    }

    public Map<Integer,TeamStanding> post(){
        //POST LEAGUE STANDING
        client = Client.create();
        webResource = client.resource(URL);
        formData = new MultivaluedMapImpl();
        formData.add("ApiKey",API_KEY);
        formData.add("league",LEAGUE_NAME);
        formData.add("seasonDateString",SEASON);
        clientResponse = webResource.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(ClientResponse.class,formData);
        if(clientResponse.getStatus() != 200){
            System.out.println("HTTP Error Code FetchLeagueStandingsBySeason: "+clientResponse.getStatus());
            System.out.println("HTTP Error Detail FetchLeagueStandingsBySeason: "+clientResponse.toString());
        }else{
            LeagueStanding ligStanding = clientResponse.getEntity(LeagueStanding.class);
            if((null!=ligStanding)&&(null!=ligStanding.getTeamTeamStandings())){
                System.out.println("TOTAL STANDINGS FROM /GetLeagueStandingsBySeason: " + ligStanding.getTeamTeamStandings().size());
                try{
                    for(TeamStanding teamTeamStanding : ligStanding.getTeamTeamStandings()){
                        this.mapItemPut(teamTeamStanding);
                    }
                }catch(Exception ex){
                    System.out.println("EXCEPTION occurred in filling TEAMSTANDING MAP: " + ex);
                }

            }else{
                System.out.println("LEAGUESTANDING ENTITY is NULL FROM REST QUERY");
            }

        }
        return leagueStandingMap;
    }

}
