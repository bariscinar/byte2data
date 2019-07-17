package net.byte2data.score.collectors;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
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
public class FetchAllTeams {

    private static URI URL;
    private static String API_KEY;

    private Map<Integer,Team> teamMap;

    private Client client;
    private WebResource webResource;
    private ClientResponse clientResponse;
    private MultivaluedMap<String, String> formData = null;

    public FetchAllTeams(String url, String apiKey) throws URISyntaxException{
        URL = new URI(url);
        API_KEY = apiKey;
        teamMap = new HashMap<>();
    }

    private void mapItemPut(Team team){
        Team putResultTeam=null;
        putResultTeam=teamMap.put(team.getId(),team);
        if(null==putResultTeam){
            //System.out.println("TEAM -> " + team.getId() + "-" + team.getLeaguename() + " is ADDED to MAP");
        }else{
            System.out.println("WARNING  -> TEAM is already EXISTED in the MAP ->  " + putResultTeam.getId() + "-" + putResultTeam.getName() + " is REPLACED with new->" +  team.getId() + "-"  + team.getName());
        }
    }

    public Map<Integer,Team> post(){
        //POST TEAMS
        client = Client.create();
        //client.addFilter(new HTTPBasicAuthFilter("user", "pass"));
        webResource = client.resource(URL);
        formData = new MultivaluedMapImpl();
        formData.add("ApiKey",API_KEY);
        clientResponse = webResource.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(ClientResponse.class,formData);
        if(clientResponse.getStatus() != 200){
            System.out.println("HTTP Error Code FetchAllTeams: "+clientResponse.getStatus());
            System.out.println("HTTP Error Detail FetchAllTeams: "+clientResponse.toString());
        }else{
            Teams teams = clientResponse.getEntity(Teams.class);
            if((null!=teams)&&(null!=teams.getTeams())){
                System.out.println("TOTAL TEAM COUNT FROM /GetAllTeams: " + teams.getTeams().size());
                try{
                    for(Team team : teams.getTeams()){
                        this.mapItemPut(team);
                    }
                }catch(Exception ex){
                    System.out.println("EXCEPTION occurred in filling TEAM map " + ex);
                }

            }else{
                System.out.println("TEAMS entity is NULL FROM REST QUERY");
            }

        }
        return teamMap;
    }

}
