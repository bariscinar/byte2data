package net.byte2data.score.collectors;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import net.byte2data.score.cover.Teams;
import net.byte2data.score.item.Team;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by barisci on 05.04.2016.
 */
public class FetchAllTeamsByLeagueAndSeason {

    private static URI URL;
    private static String API_KEY;

    private Map<Integer,Team> teamMap;

    private Client client;
    private WebResource webResource;
    private ClientResponse clientResponse;
    private MultivaluedMap<String, String> formData = null;

    public FetchAllTeamsByLeagueAndSeason(String url, String apiKey) throws URISyntaxException{
        URL = new URI(url);
        API_KEY = apiKey;
        teamMap = new HashMap<>();
    }

    private void mapItemPut(Team team){
        Team putResultTeam = teamMap.put(team.getId(),team);
        if(null==putResultTeam){
            System.out.println(team.getId() + "-" + team.getName() + " is added");
        }else{
            System.out.println("old->" + putResultTeam.getId() + "-" + putResultTeam.getName() + " is REPLACED with new->" +  team.getId() + "-"  + team.getName());
        }
    }

    public Map<Integer,Team> post(){
        //POST TEAMS BY LEAGUE AND SEASON
        client = Client.create();
        webResource = client.resource(URL);
        formData = new MultivaluedMapImpl();
        formData.add("ApiKey",API_KEY);
        formData.add("league","SERIE A");
        formData.add("seasonDateString","1516");
        clientResponse = webResource.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(ClientResponse.class,formData);
        if(clientResponse.getStatus() != 200){
            System.out.println("HTTP ErrorCode: "+clientResponse.getStatus());
            System.out.println("HTTP Error Detail: "+clientResponse.toString());
        }else{
            Teams teams = clientResponse.getEntity(Teams.class);
            if(null!=teams){
                try{
                    for(Team team : teams.getTeams()){
                        this.mapItemPut(team);
                    }
                }catch(Exception ex){
                    System.out.println("exception occurred in filling team map: " + ex);
                }

            }else{
                System.out.println("teams entity is null!!");
            }

        }
        return teamMap;
    }

}
