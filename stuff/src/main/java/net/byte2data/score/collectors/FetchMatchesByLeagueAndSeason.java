package net.byte2data.score.collectors;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import net.byte2data.score.cover.Matches;
import net.byte2data.score.item.Match;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by barisci on 05.04.2016.
 */
public class FetchMatchesByLeagueAndSeason {

    private static URI URL;
    private static String API_KEY;
    private static String LEAGUE_NAME;
    private static String SEASON;

    private Map<Integer,Match> matchMap;

    private Client client;
    private WebResource webResource;
    private ClientResponse clientResponse;
    private MultivaluedMap<String, String> formData = null;

    public FetchMatchesByLeagueAndSeason(String url, String apiKey, String leagueName,String season) throws URISyntaxException{
        URL = new URI(url);
        API_KEY = apiKey;
        LEAGUE_NAME=leagueName;
        SEASON=season;
        matchMap = new HashMap<>();
    }

    private void mapItemPut(Match match){
        Match putResultMatch = null;
        //TODO SHARE THEM WITH PROVIDER!
        if ((match.getId() <= 0) || (match.getFixtureMatchId() <= 0) || (match.getHomeTeamId() <= 0) || (match.getAwayTeamId() <= 0)) {
            System.out.println("CHECK -> MATCH is GARBAGE MATCHDATE:" + match.getDate() + " - MATCHID:"+match.getId()+ " - FIXTUREMATCHID:"+match.getFixtureMatchId());
        } else {
            if(matchMap.containsKey(match.getFixtureMatchId())){
                //TODO SHARE THEM WITH PROVIDER!
                System.out.println("CHECK -> MATCH is ALREADY EXIST IN THE MAP: "+match.getFixtureMatchId());
                System.out.println("OLD ONE");
                System.out.println(matchMap.get(match.getFixtureMatchId()).toString());
                System.out.println("NEW ONE");
                System.out.println(match.toString());
            }else{
                //TODO PLEASE CHECK THAT putResultMatch SHOULD BE ALWAYS NULL!
                putResultMatch=matchMap.put(match.getFixtureMatchId(),match);
                if(null!=putResultMatch){
                    System.out.println("OLD -> " + putResultMatch.getId() + "-" + putResultMatch.getDate() + " is REPLACED with NEW -> " +  match.getId() + "-"  + match.getDate());
                }else{
                    //System.out.println("MATCH is ADDED: "+match.getFixtureMatchId());
                }
            }
        }


    }

    public Map<Integer,Match> post(){
        //POST MATCHES BY LEAGUE AND SEASON
        client = Client.create();
        webResource = client.resource(URL);
        formData = new MultivaluedMapImpl();
        formData.add("ApiKey",API_KEY);
        formData.add("seasonDateString",SEASON);
        formData.add("league",LEAGUE_NAME);
        clientResponse = webResource.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(ClientResponse.class,formData);
        if(clientResponse.getStatus() != 200){
            System.out.println("HTTP Error Code FetchMatchesByLeagueAndSeason: "+clientResponse.getStatus());
            System.out.println("HTTP Error Detail FetchMatchesByLeagueAndSeason: "+clientResponse.toString());
        }else{
            Matches matches = clientResponse.getEntity(Matches.class);
            if((null!=matches)&&(null!=matches.getMatches())){
                System.out.println("TOTAL MATCH COUNT: " + matches.getMatches().size());
                try{
                    for(Match match : matches.getMatches()){
                        this.mapItemPut(match);
                    }
                }catch(Exception ex){
                    System.out.println("EXCEPTION occurred in filling MATCH map: " + ex);
                }

            }else{
                System.out.println("MATCHES ENTITY is NULL FROM REST QUERY");
            }

        }
        return matchMap;
    }

}
