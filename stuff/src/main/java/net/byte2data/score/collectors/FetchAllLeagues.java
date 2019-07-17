package net.byte2data.score.collectors;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import net.byte2data.score.cover.Leagues;
import net.byte2data.score.item.League;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * Created by barisci on 05.04.2016.
 */
public class FetchAllLeagues {

    private static URI URL;
    private static String API_KEY;

    private Map<Integer,League> leagueMap;

    private Client client;
    private WebResource webResource;
    private ClientResponse clientResponse;
    private MultivaluedMap<String, String> formData = null;

    public FetchAllLeagues(String url, String apiKey) throws URISyntaxException{
        URL = new URI(url);
        API_KEY = apiKey;
        leagueMap = new HashMap<>();
    }

    private void mapItemPut(League lig){
        League putResultLeague=null;
        if(leagueMap.containsKey(lig.getId())){
            //GetAllLeagues methodunun bok yemesi! Aynı leageu bilgisi farklı bir record ile ama daha az updated data ile bulunabiliyor
            if(lig.getNumberOfMatches()>leagueMap.get(lig.getId()).getNumberOfMatches()){
                putResultLeague = leagueMap.put(lig.getId(), lig);
                System.out.println("CHECK -> LEAGUE is already EXISTED in the MAP -> " + putResultLeague.getId() + "-" + putResultLeague.getName() + " is REPLACED with new->" +  lig.getId() + "-"  + lig.getName());
            }else{
                System.out.println("CHECK -> DUPLICATE LEAGUE ENTRY ->" + lig.toString());
            }
        }else{
            leagueMap.put(lig.getId(), lig);
            //System.out.println("LEAGUE -> " + lig.getId() + "-" + lig.getLeaguename() + " is ADDED to MAP");
        }
    }

    public Map<Integer,League> post(){
        //POST LEAGUES
        client = Client.create();
        webResource = client.resource(URL);
        formData = new MultivaluedMapImpl();
        formData.add("ApiKey",API_KEY);
        clientResponse = webResource.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(ClientResponse.class,formData);
        if(clientResponse.getStatus() != 200){
            System.out.println("HTTP Error Code FetchAllLeagues: "+clientResponse.getStatus());
            System.out.println("HTTP Error Detail FetchAllLeagues: "+clientResponse.toString());
        }else{
            Leagues leagues = clientResponse.getEntity(Leagues.class);
            if((null!=leagues)&&(null!=leagues.getLeagues())){
                System.out.println("TOTAL LEAGUE COUNT FROM /GetAllLeagues: " + leagues.getLeagues().size());
                try{
                    for(League league : leagues.getLeagues()){
                        this.mapItemPut(league);
                    }
                }catch(Exception ex){
                    System.out.println("EXCEPTION occurred in filling LEAGUE map: " + ex);
                }
            }else{
                System.out.println("LEAGUES entity is NULL FROM REST QUERY");
            }

        }
        return leagueMap;
    }

}
