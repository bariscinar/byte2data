package net.byte2data.score.collectors;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import net.byte2data.score.cover.Players;
import net.byte2data.score.item.Player;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by barisci on 05.04.2016.
 */
public class FetchPlayersByTeam {

    private static URI URL;
    private static String API_KEY;
    private static int TEAM_ID;

    private Map<Integer,Player> playersByTeamMap;

    private Client client;
    private WebResource webResource;
    private ClientResponse clientResponse;
    private MultivaluedMap<String, String> formData = null;

    public FetchPlayersByTeam(String url, String apiKey, int teamID) throws URISyntaxException{
        URL = new URI(url);
        API_KEY = apiKey;
        TEAM_ID = teamID;
        playersByTeamMap = new HashMap<>();
    }

    private void mapItemPut(Player player){
        Player putResult = null;
        if(playersByTeamMap.containsKey(player.getId())){
            if(playersByTeamMap.get(player.getId()).getLoanTo()!=player.getLoanTo()){
                System.out.println("!!!CHECK!!!");
                System.out.println("PLAYER:("+player.getName()+") is ALREADY EXIST in the MAP.");
                System.out.println("EXISTING: TeamId"+player.getTeamId()+"-LoanTo:"+player.getLoanTo());
                System.out.println("NEW: TeamId"+playersByTeamMap.get(player.getId()).getTeamId()+"-LoanTo:"+playersByTeamMap.get(player.getId()).getLoanTo());
            }
        }else{
            putResult = playersByTeamMap.put(player.getId(),player);
            if(null==putResult) {
                //System.out.println("("+player.getId() + "-" + player.getLeaguename() + ") is ADDED to MAP");
            }else {
                System.out.println("CHECK -> (" + putResult.getId() + "-" + putResult.getName() + ") is REPLACED with new-> (" +  player.getId() + "-"  + player.getName()+")");
            }
        }

    }

    public Map<Integer,Player> post(){
        //POST PLAYER
        client = Client.create();
        webResource = client.resource(URL);
        formData = new MultivaluedMapImpl();
        formData.add("ApiKey",API_KEY);
        formData.add("teamId",String.valueOf(TEAM_ID));
        clientResponse = webResource.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(ClientResponse.class,formData);
        if(clientResponse.getStatus() != 200){
            System.out.println("HTTP Error Code FetchPlayersByTeam: "+clientResponse.getStatus());
            System.out.println("HTTP Error Detail FetchPlayersByTeam: "+clientResponse.toString());
        }else{
            Players players = clientResponse.getEntity(Players.class);
            if((null!=players) && (null!=players.getPlayers())){
                try{
                    //System.out.println("TOTAL PLAYERS FROM /GetPlayersByTeam: " + players.getPlayers().size());
                    for(Player player : players.getPlayers()){
                        if((player.getTeamId()!=TEAM_ID)){
                            if(player.getLoanTo()==TEAM_ID){
                                //System.out.println("PLAYER ("+player.getId()+"/"+player.getLeaguename()+") is LOAN BY OUR team:"+TEAM_ID);
                                this.mapItemPut(player);
                            }else{
                                System.out.println("CHECK -> PLAYER'S TEAM IS SOMEHOW FRAUD PlayerID/PlayerName -> ("+player.getId()+"/"+player.getName()+")");
                            }
                        }else{
                            if(player.getLoanTo()>0){
                                if(player.getLoanTo()!=TEAM_ID){
                                    //System.out.println("PLAYER ("+player.getId()+"/"+player.getLeaguename()+") is loan to AN OTHER team:"+player.getLoanTo());
                                }else{
                                    //System.out.println("PLAYER ("+player.getId()+"/"+player.getLeaguename()+") is OUR CORE CORE team:"+TEAM_ID);
                                    this.mapItemPut(player);
                                }
                            }else{
                                //System.out.println("PLAYER ("+player.getId()+"/"+player.getLeaguename()+") is OUR CORE team:"+TEAM_ID);
                                this.mapItemPut(player);
                            }
                        }
                    }
                }catch(Exception ex){
                    System.out.println("EXCEPTION occurred in filling PLAYER MAP: " + ex);
                }

            }else{
                //System.out.println("PLAYERS ENTITY is NULL FROM REST QUERY TEAMID -> " + TEAM_ID);
            }

        }
        return playersByTeamMap;
    }

}
